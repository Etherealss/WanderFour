package common.strategy;

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
	 * @param partition
	 * @param order
	 * @param start
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	List<T> getWritingList(int partition, String order, Long start, int rows);


	/**
	 * 按时间获取作品
	 * @param partition
	 * @param order
	 * @param start
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	List<T> getSimpleWritingList(int partition, String order, Long start, int rows);

}
