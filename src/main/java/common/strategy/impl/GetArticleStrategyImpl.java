package common.strategy.impl;

import common.factory.DaoFactory;
import common.strategy.GetWritingStrategy;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
public class GetArticleStrategyImpl implements GetWritingStrategy<Article> {

	private WritingDao<Article> dao = DaoFactory.getArticleDao();

	private Logger logger = Logger.getLogger(GetArticleStrategyImpl.class);

	@Override
	public List<Article> getWritingList(Connection conn, int partition, String order, Long start, int rows) throws SQLException {
		return dao.getWritingListByOrder(conn, partition, order, start, rows);
	}


	@Override
	public List<Article> getSimpleWritingList(Connection conn, int partition, String order, Long start, int rows) throws SQLException {
		return dao.getSimpleWritingListByOrder(conn, partition, order, start, rows);
	}
}
