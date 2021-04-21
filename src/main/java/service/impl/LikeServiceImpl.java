package service.impl;

import common.enums.LikeEnum;
import common.enums.ResultType;
import common.enums.TargetType;
import common.strategy.choose.LikeStategyChoose;
import common.strategy.impl.LikeStrategyImpl;
import common.strategy.impl.CancelLikeStrategyImpl;
import common.util.JedisUtil;
import dao.ArticleDao;
import dao.LikeDao;
import dao.PostsDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.po.LikeRecord;
import redis.clients.jedis.Jedis;
import service.LikeService;

import java.util.Map;

/**
 * @author 寒洲
 * @description 点赞service
 * @date 2020/10/14
 */
public class LikeServiceImpl implements LikeService {

	private Logger logger = Logger.getLogger(LikeServiceImpl.class);

	@Autowired
	private LikeDao likeDao;

	//预设两个DAO，理论上每次都会用到两个DAO

	@Autowired
	private ArticleDao aDao;
	@Autowired
	private PostsDao pDao;

	@Override
	public ResultType likeOrUnlike(LikeRecord likeRecord) {
		//检查
		if (likeRecord.getTargetType() == null) {
			logger.error("点赞类型为null 异常！");
			return ResultType.EXCEPTION;
		}
		//获取属性
		Long userid = likeRecord.getUserid();
		Long targetId = likeRecord.getTargetId();
		int likeState = likeRecord.getLikeState();
		TargetType likeType = likeRecord.getTargetType();

		if (likeState == 1) {
			//想要点赞
			logger.info("用户" + likeRecord.getUserid() + "对" +
					likeRecord.getTargetType() + "点赞，id为："
					+ likeRecord.getTargetId());
			LikeStategyChoose stategyChoose = new LikeStategyChoose(new LikeStrategyImpl());
			stategyChoose.likeOperator(userid, targetId, likeType);
		} else if (likeState == 0) {
			//想要取消点赞
			logger.info("用户" + likeRecord.getUserid() + "取消了对" +
					likeRecord.getTargetType() + "的点赞，id为："
					+ likeRecord.getTargetId());
			LikeStategyChoose stategyChoose = new LikeStategyChoose(new CancelLikeStrategyImpl());
			stategyChoose.likeOperator(userid, targetId, likeType);
		}
		return ResultType.SUCCESS;
	}

	@Override
	public void persistLikeRecord() {
		//储存用户点赞关系
		logger.info("储存用户点赞关系");
		Jedis jedis = JedisUtil.getJedis();
		Map<String, String> redisLikeData = jedis.hgetAll(LikeEnum.KEY_LIKE_RECORD);

		String likeTableName;
		//获取键值
		for (Map.Entry<String, String> vo : redisLikeData.entrySet()) {

			String likeRecordKey = vo.getKey();
			LikeRecord likeRecord = getLikeRecord(likeRecordKey);

			String value = vo.getValue();
			logger.debug(likeRecord + ", value = " + value);

			// 根据TargetType获取表名
			likeTableName = TargetType.getLikeTableNameByTargetType(likeRecord.getTargetType());

			//检查数据库的点赞状态，true为存在点赞记录
			boolean b = likeDao.countUserLikeRecord(likeTableName, likeRecord) == 1;
			logger.debug("数据库点赞状态：" + b);

			if (LikeEnum.HAVE_LIKED.equals(value)) {
				//储存点赞记录
				if (!b) {
					//未点赞，添加记录
					likeDao.createLikeRecord(likeTableName, likeRecord);
					logger.trace("添加点赞记录");
				}
				//else 已点赞，不操作

			} else if (LikeEnum.HAVE_NOT_LIKED.equals(value)) {
				//删除点赞记录
				if (b) {
					//数据库存在用户点赞记录，删除该记录，取消点赞
					likeDao.deleteLikeRecord(likeTableName, likeRecord);
					logger.trace("删除点赞记录");
				}
			}
		}
		//在缓存数据都成功添加到数据库后再删除数据，防止回滚丢失数据
		for (String key : redisLikeData.keySet()) {
			//根据key移除
			jedis.hdel(LikeEnum.KEY_LIKE_RECORD, key);
		}
	}


	@Override
	public void persistLikeCount() {
		Jedis jedis = JedisUtil.getJedis();
		// 获取所有缓存的点赞键值对（包含了目标对象的类型和id以及缓存的点赞数）
		Map<String, String> redisLikeData = jedis.hgetAll(LikeEnum.KEY_LIKE_COUNT);

		//获取键值
		for (Map.Entry<String, String> vo : redisLikeData.entrySet()) {
			String likeRecordKey = vo.getKey();

			String[] splitKey = likeRecordKey.split("::");
			// 点赞的目标id
			Long id = Long.valueOf(splitKey[1]);
			// 缓存的点赞数
			int count = Integer.parseInt(vo.getValue());
			logger.debug("id = " + id + ", count = " + count);

			//判断点赞类型
			if (String.valueOf(TargetType.ARTICLE.code()).equals(splitKey[0])) {
				// 点赞了文章
				// 获取文章当前的点赞数
				int likeCount = aDao.getLikeCount(id);
				// 获取最终点赞数
				int result = count + likeCount;
				// 更新点赞数
				aDao.updateLikeCount(id, result);
			} else if (String.valueOf(TargetType.POSTS.code()).equals(splitKey[0])) {
				// 点赞了问贴
				// 获取问贴当前的点赞数
				int likeCount = pDao.getLikeCount(id);
				// 获取最终点赞数
				int result = count + likeCount;
				// 更新点赞数
				pDao.updateLikeCount(id, result);

			}
		}
		for (String key : redisLikeData.keySet()) {
			//储存数据成功后移出redis
			jedis.hdel(LikeEnum.KEY_LIKE_COUNT, key);
		}
		jedis.close();
	}

	/**
	 * 将redis的数据封装到实例中
	 * @param keys "targetType::userid::targetId"
	 * @return
	 */
	private LikeRecord getLikeRecord(String keys) {
		//切割获取数据
		String[] splitKey = keys.split("::");
		LikeRecord record = new LikeRecord();
		record.setTargetType(Integer.parseInt(splitKey[0]));
		record.setUserid(Long.valueOf(splitKey[1]));
		record.setTargetId(Long.valueOf(splitKey[2]));
		logger.debug(record);

		return record;
	}
}
