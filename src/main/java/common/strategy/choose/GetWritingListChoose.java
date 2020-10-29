package common.strategy.choose;

import common.enums.DaoEnum;
import common.strategy.GetWritingStrategy;
import pojo.po.Writing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取作品列表的策略
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

		//按点赞数
		List<T> writingByLike = strategy.getWritingList(conn, partition, DaoEnum.FIELD_ORDER_BY_TIME,
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

		//按时间
		List<T> writingByTime = strategy.getWritingList(conn, partition, DaoEnum.FIELD_ORDER_BY_TIME,
				DaoEnum.START_FROM_ZERO, DaoEnum.ROWS_SIX);
		return writingByTime;
	}

	/**
	 * 按点赞获取仅包含标题的id的作品列表
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	public List<T> getSimpleByLike(Connection conn, int partition) throws SQLException {

		List<T> writingByLike = strategy.getSimpleWritingList(conn, partition, DaoEnum.FIELD_ORDER_BY_TIME,
				DaoEnum.START_FROM_ZERO, DaoEnum.ROWS_FIVE);
		return writingByLike;
	}

	/**
	 * 按时间获取仅包含标题的id的作品列表
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	public List<T> getSimpleByTime(Connection conn, int partition) throws SQLException {

		List<T> writingByTime = strategy.getSimpleWritingList(conn, partition, DaoEnum.FIELD_ORDER_BY_TIME,
				DaoEnum.START_FROM_ZERO, DaoEnum.ROWS_FIVE);
		return writingByTime;
	}
}
