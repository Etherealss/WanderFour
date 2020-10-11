package service.impl;

import common.dto.ResultState;
import common.factory.DaoFactory;
import dao.WritingDao;
import pojo.po.Posts;
import service.WritingService;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class PostsServiceImpl implements WritingService<Posts> {

	private WritingDao<Posts> dao = DaoFactory.getPostsDao();

	@Override
	public ResultState publishNewWriting(Posts posts) {
		return null;
	}

	@Override
	public Posts getWriting(Long id) {
		return null;
	}

	@Override
	public ResultState updateWriting(Posts posts) {
		return null;
	}

	@Override
	public ResultState deleteWriting(Long id) {
		return null;
	}
}
