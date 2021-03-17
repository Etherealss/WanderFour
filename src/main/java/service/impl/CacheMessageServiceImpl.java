package service.impl;

import common.util.JedisUtil;
import redis.clients.jedis.Jedis;
import service.CacheMessageService;

/**
 * @author 寒洲
 * @description 回显信息
 * @date 2020/11/19
 */
public class CacheMessageServiceImpl implements CacheMessageService {

	@Override
	public void cacheMessageToJedis() throws Exception {
		Jedis jedis = JedisUtil.getJedis();
	}
}
