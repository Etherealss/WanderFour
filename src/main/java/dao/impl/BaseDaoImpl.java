package dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class BaseDaoImpl {
	private Logger logger = Logger.getLogger(BaseDaoImpl.class);
	protected QueryRunner qr = new QueryRunner();

	/**
	 * 获取合适的数据库Id
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	protected BigInteger selectLastInsertId(Connection conn) throws SQLException {
		String sql = "SELECT LAST_INSERT_ID();";
		return qr.query(conn, sql, new ScalarHandler<BigInteger>());
	}
}
