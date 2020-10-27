package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
public interface SensitiveDao {

	/**
	 * 插入敏感词
	 * @param conn
	 * @param type
	 * @param word
	 * @throws SQLException
	 * @return
	 */
	int insertSensitiveDao(Connection conn, int type, String word) throws SQLException;


	/**
	 * 获取敏感词
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	List<String> getSensitiveWordsList(Connection conn) throws SQLException;
}
