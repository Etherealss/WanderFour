package common.strategy.impl;

import common.enums.LikeEnum;
import common.enums.TargetType;
import common.strategy.LikeStrategy;
import common.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * @author 寒洲
 * @description 点赞策略
 * @date 2020/10/14
 */
public class LikeStrategyImpl extends LikeStrategy {

	/**
	 * 点赞的redis value
	 */
	private static final String LIKE_STATE = "1";

	@Override
	public void likeOperate(Long userid, Long targetId, TargetType targetType) {
		/*
		以"targetType::userid::targetId"为redis的field，点赞状态为值
		点赞状态分为 1-已点赞 0-未点赞，可能未来会有踩，设为-1
		 */
		logger.trace("userid=" + userid + ", targetId=" + targetId + ", likeState=" + LIKE_STATE + ", targetType=" + targetType);
		//获取存入redis的域名
		//点赞关系的域名
		String likeRecordFieldName = getLikeFieldName(userid, targetId, targetType.code());
		//用于点赞数量统计的域名
		String likeCountFieldName = getLikeFieldName(targetId, targetType.code());

		Jedis jedis = JedisUtil.getJedis();
		// 获取用户点赞的数据，以userid和targetId为field，表为id
		String recordState = jedis.hget(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldName);
		logger.debug("redis点赞缓存：" + recordState);

		//缓存点赞关系
		if (LikeEnum.HAVE_LIKED.equals(recordState)) {
			//TODO 已缓存点赞
		} else {
			//未点赞或者无记录，修改记录。
			//之后在缓存数据持久化到数据库时会检查是否已点赞过
			logger.trace("未点赞或者无记录，修改缓存记录，暂不检查数据库");
			jedis.hset(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldName, LIKE_STATE);

			/*
			更新缓存的点赞数量，点赞数+1
			如果没有记录，会添加记录，并执行hincrby操作
			 */
			jedis.hincrBy(LikeEnum.KEY_LIKE_COUNT, likeCountFieldName, 1L);
		}

		jedis.close();
	}
}
