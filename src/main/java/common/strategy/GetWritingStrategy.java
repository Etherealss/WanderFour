package common.strategy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取作品(Writing)的策略
 * @date 2020/10/27
 */
public interface GetWritingStrategy<T> {

	/**
	 * 按时间获取作品
	 * @param conn
	 * @param partition
	 * @param start
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	List<T> getWritingByTime(Connection conn, int partition, Long start, int rows) throws SQLException;

	/**
	 * 按点赞数获取作品
	 * @param conn
	 * @param partition
	 * @param start
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	List<T> getWritingByLike(Connection conn, int partition, Long start, int rows) throws SQLException;

}
