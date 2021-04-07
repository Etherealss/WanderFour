package common.strategy.impl;

import common.strategy.GetWritingStrategy;
import dao.ArticleDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
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

	private Logger logger = Logger.getLogger(GetArticleStrategyImpl.class);

	@Override
	public List<Article> getWritingList(int partition, String order, Long start, int rows) {
		return dao.getWritingListByOrder(partition, order, start, rows);
	}


	@Override
	public List<Article> getSimpleWritingList(int partition, String order, Long start, int rows) {
		return dao.getSimpleWritingListByOrder(partition, order, start, rows);
	}
}
