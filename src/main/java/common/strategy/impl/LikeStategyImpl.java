package common.strategy.impl;

import common.enums.LikeEnum;
import common.enums.TargetType;
import common.factory.DaoFactory;
import common.strategy.LikeStategy;
import common.util.JedisUtil;
import dao.LikeDao;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

/**
 * @author 寒洲
 * @description 点赞策略
 * @date 2020/10/14
 */
public class LikeStategyImpl implements LikeStategy {

	private Logger logger = Logger.getLogger(LikeStategyImpl.class);

	@Override
	public void like(Long userid, Long targetId, int likeState, TargetType targetType) {
		/*
		以"targetType::userid::targetId"为redis的field，点赞状态为值
		点赞状态分为 1-已点赞 0-未点赞，可能未来会有踩，设为-1
		 */
		logger.trace("userid=" + userid + ", targetId=" + targetId + ", likeState=" + likeState + ", targetType=" + targetType);
		//获取存入redis的域名
		//点赞关系的域名
		String likeRecordFieldKey = getLikeKey(userid, targetId, targetType.code());
		//用于点赞数量统计的域名
		String likeCountFieldKey = getLikeKey(targetId, targetType.code());

		Jedis jedis = JedisUtil.getJedis();
		// 获取用户点赞的数据，以userid和targetId为field，表为id
		String recordState = jedis.hget(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey);
		logger.debug("redis点赞缓存：" + recordState);

		//缓存点赞关系
		if (LikeEnum.HAVE_LIKED.equals(recordState)) {
			//TODO 已缓存点赞
		} else {
			//未点赞或者无记录，修改记录。
			//之后在缓存数据持久化到数据库时会检查是否已点赞过
			logger.trace("未点赞或者无记录，修改缓存记录，暂不检查数据库");
			jedis.hset(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey, String.valueOf(likeState));

			/*
			更新缓存的点赞数量，点赞数+1
			如果没有记录，会添加记录，并执行hincrby操作
			 */
			jedis.hincrBy(LikeEnum.KEY_LIKE_COUNT, likeCountFieldKey, 1L);
		}

		jedis.close();
	}

	@Override
	public void unlike(Long userid, Long targetId, int likeState, TargetType targetType) {
		//点赞关系的域名
		String likeRecordFieldKey = getLikeKey(userid, targetId, targetType.code());
		//用于点赞数量统计的域名
		String likeCountFieldKey = getLikeKey(targetId, targetType.code());

		logger.debug(likeRecordFieldKey);

		Jedis jedis = JedisUtil.getJedis();

		// 获取用户点赞的数据，以userid和targetId为key，表为id
		String likeRecordState = jedis.hget(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey);

		logger.debug(likeRecordState);

		if (LikeEnum.HAVE_LIKED.equals(likeRecordState)) {
			//已点赞，取消点赞
			logger.debug("已点赞，取消点赞");
			jedis.hdel(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey);

			/*
			更新缓存的点赞数量，点赞数+1
			如果没有记录，会添加记录，并执行hincrby操作
			 */
			jedis.hincrBy(LikeEnum.KEY_LIKE_COUNT, likeCountFieldKey, -1L);
		} else {
			//TODO 未点赞或者无记录
		}

		jedis.close();
	}

	@Override
	public void dislike(Long userid, Long targetId, int likeState, TargetType likeType) {

	}

	/**
	 * 获取redis缓存的点赞关系的域名
	 * @param userid
	 * @param targetId
	 * @param targetType
	 * @return 形如"targetType::userid::targetId"
	 */
	private String getLikeKey(Long userid, Long targetId, int targetType) {
		String likeKey = targetType + "::" + userid + "::" + targetId;
		return likeKey;
	}

	/**
	 * 获取redis缓存的点赞数量的域名
	 * @param targetId
	 * @param targetType
	 * @return 形如"targetType::targetId"
	 */
	private String getLikeKey(Long targetId, int targetType) {
		String likeKey = targetType + "::" + targetId;
		return likeKey;
	}
}
