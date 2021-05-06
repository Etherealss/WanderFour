package common.strategy.impl;

import common.strategy.GetWritingStrategy;
import dao.ArticleDao;
import dao.WritingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.po.Article;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
public class GetArticleStrategyImpl implements GetWritingStrategy<Article> {

	private WritingDao<Article> dao;

	public GetArticleStrategyImpl(ArticleDao dao) {
		this.dao = dao;
	}

	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	@Override
	public List<Article> getWritingList(int partition, String order, Long start, int rows) {
		return dao.getWritingListByOrder(partition, order, start, rows);
	}


	@Override
	public List<Article> getSimpleWritingList(int partition, String order, Long start, int rows) {
		return dao.getSimpleWritingListByOrder(partition, order, start, rows);
	}
}
