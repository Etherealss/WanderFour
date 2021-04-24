package common.strategy.impl;

import common.enums.LikeEnum;
import common.enums.TargetType;
import common.strategy.LikeStrategy;
import common.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * @author 寒洲
 * @description 取消点赞策略
 * @date 2020/10/23
 */
public class CancelLikeStrategyImpl extends LikeStrategy {

    /**
     * 取消点赞的redis value
     */
    private static final String UNLIKE_STATE = "0";

    @Override
    public void likeOperate(Long userid, Long targetId, TargetType targetType) {
        //点赞关系的域名
        String likeRecordFieldKey = getLikeFieldName(userid, targetId, targetType.code());
        //用于点赞数量统计的域名
        String likeCountFieldKey = getLikeFieldName(targetId, targetType.code());

        logger.debug(likeRecordFieldKey);

        Jedis jedis = JedisUtil.getJedis();

        // 获取用户点赞的数据，以userid和targetId为key，表为id
        String likeRecordState = jedis.hget(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey);

        /*
        不同于点赞时的操作，取消点赞时，缓存中基本不会有已点赞的缓存（这些数据存在数据库）
        所以这里不判断是否已有缓存记录
        不论是否已有缓存都要执行下面的操作
         */
        //将value设为0，这样子就记录了取消点赞的状态，可以持久化到数据库
        jedis.hset(LikeEnum.KEY_LIKE_RECORD, likeRecordFieldKey, UNLIKE_STATE);
		/*
		更新缓存的点赞数量，点赞数+1
		如果没有记录，会添加记录，并执行hincrby操作
		 */
        jedis.hincrBy(LikeEnum.KEY_LIKE_COUNT, likeCountFieldKey, -1L);

        jedis.close();
    }


}
