package common.strategy.impl;

import common.enums.DaoEnum;
import common.factory.DaoFactory;
import common.strategy.GetWritingStrategy;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Posts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
public class GetPostsStrategyImpl implements GetWritingStrategy<Posts> {

	private WritingDao<Posts> dao = DaoFactory.getPostsDao();

	private Logger logger = Logger.getLogger(GetArticleStrategyImpl.class);

	@Override
	public List<Posts> getWritingByTime(Connection conn, int partition, Long start, int rows) throws SQLException {
		return dao.getWritingListByOrder(conn, partition, DaoEnum.FIELD_ORDER_BY_TIME, start, rows);
	}

	@Override
	public List<Posts> getWritingByLike(Connection conn, int partition, Long start, int rows) throws SQLException {
		return dao.getWritingListByOrder(conn, partition, DaoEnum.FIELD_ORDER_BY_LIKE, start, rows);
	}
}
