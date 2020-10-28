package common.strategy.choose;

import common.enums.DaoEnum;
import common.strategy.GetWritingStrategy;
import pojo.po.Writing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
public class GetWritingListChoose<T extends Writing> {

	private GetWritingStrategy<T> strategy;

	public GetWritingListChoose(GetWritingStrategy<T> strategy) {
		this.strategy = strategy;
	}

	/**
	 * 按点赞获取
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	public List<T> getByLike(Connection conn, int partition) throws SQLException {

		List<T> writingByLike = strategy.getWritingByLike(conn, partition,
				DaoEnum.START_FROM_ZERO, DaoEnum.ROWS_SIX);
		return writingByLike;
	}

	/**
	 * 按时间获取
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	public List<T> getByTime(Connection conn, int partition) throws SQLException {

		List<T> writingByTime = strategy.getWritingByTime(conn, partition,
				DaoEnum.START_FROM_ZERO, DaoEnum.ROWS_SIX);
		return writingByTime;
	}

}
