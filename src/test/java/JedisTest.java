import common.enums.TargetType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;


/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public class JedisTest {
	Logger log = Logger.getLogger(JedisTest.class);
	Jedis jedis;

	@Before
	public void init() {
		jedis = JedisUtil.getJedis();
	}
	@After
	public void closeConn() throws Exception {
		jedis.close();
	}

	@Test
	public void jedisUtil() {
		Jedis jedis = JedisUtil.getJedis();
		jedis.set("username", "zhangsan");
		log.debug(jedis.get("username"));
	}

	@Test
	public void jedisTest1() {
		String userid = "123123@qq.com";
		long targetId = 2L;
		String targetType = TargetType.ARTICLE.val();
		long date = new Date().getTime();
		String key = userid+"::"+ targetId;
		String value = "1::" + date +"::"+ targetType;
		jedis.hset(key, "value", value);
		String value1 = jedis.hget(key, "value");
		String[] split = value1.split("::");
		for (String s :split){
			System.out.println(s);
		}
		Date date1 = new Date(date);
		System.out.println(date1);
	}

	@Test
	public void jedisTest2() {
		String userid = "123123@qq.com";
		long targetId = 2L;
		String targetType = TargetType.ARTICLE.val();
		long date = new Date().getTime();
		String key = userid+"::"+ targetId;
		jedis.hset(key, "type", targetType);
		jedis.hset(key, "date", String.valueOf(date));
		System.out.println(jedis.hget(key, "type"));
		System.out.println(jedis.hget(key, "date"));
	}
}
