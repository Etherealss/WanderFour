package service.impl;

import common.enums.LikeEnum;
import common.enums.ResultType;
import common.enums.TargetType;
import common.factory.DaoFactory;
import common.strategy.choose.LikeStategyChoose;
import common.strategy.impl.LikeStrategyImpl;
import common.strategy.impl.CancelLikeStrategyImpl;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import dao.LikeDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Article;
import pojo.po.LikeRecord;
import pojo.po.Posts;
import redis.clients.jedis.Jedis;
import service.LikeService;

import java.sql.Connection;
import java.util.Map;

/**
 * @author 寒洲
 * @description 点赞service
 * @date 2020/10/14
 */
public class LikeServiceImpl implements LikeService {

	private Logger logger = Logger.getLogger(LikeServiceImpl.class);

	LikeDao articleLikeDao;
	LikeDao postsLikeDao;
	LikeDao commentLikeDao;

	@Override
	public ResultType likeOrUnlike(LikeRecord likeRecord) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		//检查
		if (likeRecord.getTargetType() == null) {
			logger.debug("点赞类型为null 异常！");
			throw new Exception("点赞类型为null");
		}
		//获取属性
		Long userid = likeRecord.getUserid();
		Long targetId = likeRecord.getTargetId();
		int likeState = likeRecord.getLikeState();
		TargetType likeType = likeRecord.getTargetType();

		if (likeState == 1) {
			//想要点赞
			LikeStategyChoose stategyChoose = new LikeStategyChoose(new LikeStrategyImpl());
			stategyChoose.likeOperator(userid, targetId, likeType);
		} else if (likeState == 0) {
			//想要取消点赞
			LikeStategyChoose stategyChoose = new LikeStategyChoose(new CancelLikeStrategyImpl());
			stategyChoose.likeOperator(userid, targetId, likeType);
		}
		return ResultType.SUCCESS;
	}

	@Override
	public void persistLikeRecord() throws Exception {
		//储存用户点赞关系

		Connection conn = JdbcUtil.getConnection();
		Jedis jedis = JedisUtil.getJedis();
		Map<String, String> redisLikeData = jedis.hgetAll(LikeEnum.KEY_LIKE_RECORD);

		//实例化三个点赞DAO
		createDaoInstance();

		//获取键值
		for (Map.Entry<String, String> vo : redisLikeData.entrySet()) {

			String likeRecordKey = vo.getKey();
			LikeRecord likeRecord = getLikeRecord(likeRecordKey);

			String value = vo.getValue();
			logger.debug(likeRecord + ", value = " + value);


			//根据不同的类型使用不同的预设DAO
			LikeDao dao = getLikeDaoByTargetType(likeRecord.getTargetType());

			//检查数据库的点赞状态，true为存在点赞记录
			boolean b = dao.countUserLikeRecord(conn, likeRecord);
			logger.debug("数据库点赞状态：" + b);

			if (LikeEnum.HAVE_LIKED.equals(value)) {
				//储存点赞记录
				if (!b) {
					//未点赞，添加记录
					dao.createLikeRecord(conn, likeRecord);
					logger.trace("添加点赞记录");
				}
				//else 已点赞，不操作

			} else if (LikeEnum.HAVE_NOT_LIKED.equals(value)) {
				//删除点赞记录
				if (b) {
					//数据库存在用户点赞记录，删除该记录，取消点赞
					dao.deleteLikeRecord(conn, likeRecord);
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

	/**
	 * 实例化三个点赞DAO
	 */
	private void createDaoInstance() {
		logger.trace("实例化三个点赞DAO");
		articleLikeDao = DaoFactory.getLikeDao(TargetType.ARTICLE);
		postsLikeDao = DaoFactory.getLikeDao(TargetType.POSTS);
		commentLikeDao = DaoFactory.getLikeDao(TargetType.COMMMENT);
	}

	/**
	 * 根据不同的类型使用不同的DAO
	 * @param type
	 * @return
	 */
	private LikeDao getLikeDaoByTargetType(TargetType type) {
		LikeDao dao;
		//判断请求的类型
		switch (type) {
			case ARTICLE:
				/*
				原本此处会判断实例是否为null，如果为null则创建实例
				但是在循环中，只有第一次是需要创建实例的
				而后续的判空操作都有些冗余
				所以我把创建实例单独出一个方法了，这里不再判断DAO是否为null
				 */
				dao = articleLikeDao;
				break;
			case POSTS:
				dao = postsLikeDao;
				break;
			default:
				dao = commentLikeDao;
		}
		return dao;
	}

	@Override
	public void persistLikeCount() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		Jedis jedis = JedisUtil.getJedis();
		// 获取所有缓存的点赞键值对（包含了目标对象的类型和id以及缓存的点赞数）
		Map<String, String> redisLikeData = jedis.hgetAll(LikeEnum.KEY_LIKE_COUNT);

		//预设两个DAO，理论上每次都会用到两个DAO
		WritingDao<Article> aDao = DaoFactory.getArticleDao();
		WritingDao<Posts> pDao = DaoFactory.getPostsDao();

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
				int likeCount = aDao.getLikeCount(conn, id);
				// 获取最终点赞数
				int result = count + likeCount;
				// 更新点赞数
				aDao.updateLikeCount(conn, id, result);
			} else if (String.valueOf(TargetType.POSTS.code()).equals(splitKey[0])) {
				// 点赞了问贴
				// 获取问贴当前的点赞数
				int likeCount = pDao.getLikeCount(conn, id);
				// 获取最终点赞数
				int result = count + likeCount;
				// 更新点赞数
				pDao.updateLikeCount(conn, id, result);

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
