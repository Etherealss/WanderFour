package service.impl;

import common.enums.ResultType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Posts;
import service.WritingService;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/20
 */
public class PostsServiceImpl implements WritingService<Posts> {

	private Logger logger = Logger.getLogger(PostsServiceImpl.class);
	private WritingDao<Posts> dao = DaoFactory.getPostsDao();

	@Override
	public Long publishNewWriting(Posts posts) throws Exception {
		logger.trace("发表新问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			logger.debug(posts);

			//添加问贴
			boolean b = dao.createWritingInfo(conn, posts);
			//获取自增的主键Id
			Long maxId = dao.getLastInsertId(conn).longValue();
			logger.debug("maxId = " + maxId);
			if (b) {
				// 两次操作均无异常时返回
				return maxId;
			} else {
				throw new Exception("发表新问贴异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Posts getWriting(Long id) {
		logger.trace("获取问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			Posts posts = dao.getWritingById(conn, id);
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultType updateWriting(Posts posts) {
		logger.trace("修改问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			// 检查修改文章的用户是否为作者
			if (posts.getAuthorId().equals(dao.getAuthorByWritingId(conn, posts.getId()))) {
				boolean b = dao.updateWritingInfo(conn, posts);
				if (b) {
					return ResultType.SUCCESS;
				} else {
					throw new Exception("修改问贴异常");
				}
			} else {
				return ResultType.NOT_AUTHOR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultType.EXCEPTION;
		}
	}

	@Override
	public ResultType deleteWriting(Long writingId, Long deleterId) {
		logger.trace("删除问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			// 检查是否作者本人删除
			if (deleterId.equals(dao.getAuthorByWritingId(conn, writingId))) {
				//如果是，执行删除操作
				boolean b = dao.deleteWritingById(conn, writingId);
				if (b) {
					return ResultType.SUCCESS;
				} else {
					throw new Exception("删除问贴异常");
				}
			} else {
				return ResultType.NOT_AUTHOR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultType.EXCEPTION;
		}
	}
}
