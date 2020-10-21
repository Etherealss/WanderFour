package common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public class JedisUtil {

	/**
	 * Jedis连接池
	 */
	private static JedisPool pool;

	static {
		setJedisPool();
	}

	/**
	 * 初始化连接池
	 * @return
	 */
	private static void setJedisPool() {
		//获取配置文件
		InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
		Properties prop = new Properties();
		try {
			//读取
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal")));
		poolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
		String host = prop.getProperty("host");
		int port = Integer.parseInt(prop.getProperty("port"));
		//初始化
		pool = new JedisPool(poolConfig, host, port);
	}

	/**
	 * 获取Jedis连接
	 * @return
	 */
	public static Jedis getJedis() {
		return pool.getResource();
	}

	/**
	 * 关闭Jedis连接池
	 */
	public static void closeJedisPool(){
		pool.close();
	}
}
