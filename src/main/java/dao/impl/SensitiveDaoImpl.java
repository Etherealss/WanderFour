package dao.impl;

import dao.SensitiveDao;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
public class SensitiveDaoImpl extends BaseDaoImpl implements SensitiveDao {
	@Override
	public int insertSensitiveDao(Connection conn, int type, String word) throws SQLException {
		String sql = "insert into `wanderfour`.sensitive_word (`sentitive_word`,`type`) values (?,?)";
		return qr.update(conn, sql, word, type);
	}

	@Override
	public List<String> getSensitiveWordsList(Connection conn) throws SQLException {
		String sql = "SELECT `sentitive_word` FROM `sensitive_word`;";
		List<String> query = qr.query(conn, sql, new ColumnListHandler<String>());
		return query;
	}
}
