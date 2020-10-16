package common.strategy.impl;

import common.enums.TargetType;
import common.strategy.LikeStategy;
import common.util.JedisUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @author 寒洲
 * @description 点赞策略
 * @date 2020/10/14
 */
public class LikeStategyImpl implements LikeStategy {

	private Logger logger = Logger.getLogger(LikeStategyImpl.class);

	@Override
	public void like(String userid, Long targetId, int likeState, TargetType likeType) {
		logger.trace("userid=" + userid + ", targetId=" + targetId + ", likeState=" + likeState + ", likeType=" + likeType);
		String likeKey = getLikeKey(userid, targetId);
		Jedis jedis = JedisUtil.getJedis();
		// 获取用户点赞的数据，以userid和targetId为key，表为id
		int count = Integer.parseInt(jedis.hget(likeKey, "state"));
		if (count == likeState) {
			//TODO 已点赞
		} else {
			//未点赞或者无记录，修改记录
			jedis.hset(likeKey, "state", String.valueOf(likeState));
			jedis.hset(likeKey, "type", likeType.val());
		}
		jedis.close();
	}

	@Override
	public void unlike(String userid, Long targetId, int likeState, TargetType likeType) {
		logger.trace("userid=" + userid + ", targetId=" + targetId + ", likeState=" + likeState + ", likeType=" + likeType);
		String likeKey = getLikeKey(userid, targetId);
		Jedis jedis = JedisUtil.getJedis();
		// 获取用户点赞的数据，以userid和targetId为key，表为id
		int count = Integer.parseInt(jedis.hget(likeKey, "state"));
		if (count == likeState) {
			//已点赞，取消点赞
			jedis.hdel(likeKey, "state", "type", "date");
		} else {
			//TODO 未点赞或者无记录

		}
		jedis.close();
	}

	@Override
	public void dislike(String userid, Long targetId, int likeState, TargetType likeType) {

	}

	private String getLikeKey(String userid, Long targetId) {
		String likeKey = userid + "::" + targetId;
		return likeKey;
	}

}
