package common.strategy.impl;

import common.strategy.GetWritingStrategy;
import dao.PostsDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Posts;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
public class GetPostsStrategyImpl implements GetWritingStrategy<Posts> {

	private WritingDao<Posts> dao;

	public GetPostsStrategyImpl(PostsDao dao) {
		this.dao = dao;
	}

	private Logger logger = Logger.getLogger(GetArticleStrategyImpl.class);

	@Override
	public List<Posts> getWritingList(int partition, String order, Long start, int rows) {
		return dao.getWritingListByOrder(partition, order, start, rows);
	}

	@Override
	public List<Posts> getSimpleWritingList(int partition, String order, Long start, int rows) {
		return dao.getSimpleWritingListByOrder(partition, order, start, rows);
	}

}
