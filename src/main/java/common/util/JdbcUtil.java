package common.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 寒洲
 * @description 数据库连接池工具类 —— Druid
 * @date 2020/7/24
 */
public class JdbcUtil {
	/** 数据库DataSource */
	private static DataSource source = null;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	private static Logger logger = Logger.getLogger(JdbcUtil.class);

	static {
		InputStream is = null;
		Properties pros;
		try {
			pros = new Properties();
			is = JdbcUtil.class.getClassLoader().getResourceAsStream("druid.properties");
			pros.load(is);
			source = DruidDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取数据库连接
	 * @return Connection 数据库的连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception {
		Connection conn = threadLocal.get();
		//如果是第一次，就创建一个连接
		if (conn == null) {
			logger.trace("创建一个数据库连接");
			conn = source.getConnection();
			//添加到本地的线程变量
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * 开启事务
	 * @throws Exception
	 */
	public static Connection beginTransaction() throws Exception {
		logger.trace("开启事务，获取连接，关闭自动提交");
		Connection conn = getConnection();
		//关闭自动提交，开启事务
		conn.setAutoCommit(false);
		return conn;
	}

	/**
	 * 提交事务
	 * @throws Exception
	 */
	public static void commitTransaction() throws Exception {
		Connection conn = getConnection();
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * 回滚事务
	 * @throws Exception
	 */
	public static void rollbackTransaction() throws Exception {
		logger.trace("获取连接，回滚事务");
		Connection conn = getConnection();
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * 结束事务，关闭连接
	 * @throws Exception
	 */
	public static void closeTransaction() throws Exception {
		logger.trace("获取连接，结束事务");
		Connection conn = getConnection();
		if (conn != null) {
			conn.close();
		}
		threadLocal.remove();
	}

	/**
	 * 开启事务
	 * @throws Exception
	 */
	public static Connection beginTransactionForTest() throws Exception {
		logger.trace("开启事务，开启自动提交");
		Connection conn = getConnection();
		//关闭自动提交，开启事务
		conn.setAutoCommit(true);
		return conn;
	}

}
