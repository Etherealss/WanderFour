package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetPostsStrategyImpl;
import common.util.FileUtil;
import common.util.JdbcUtil;
import dao.UserDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.dto.WritingBean;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.User;
import service.WritingService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
	public WritingBean<Posts> getWriting(Long id) {
		logger.trace("获取问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			Posts posts = dao.getWritingById(conn, id);
			WritingBean<Posts> bean = new WritingBean<>();
			UserDao userDao = DaoFactory.getUserDAO();
			User userInfo = userDao.getImgAndNicknameById(conn, posts.getAuthorId());
			byte[] imgStream = FileUtil.getFileStream(userInfo.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);
			bean.setUserImg(imgByBase64);
			bean.setWriting(posts);
			bean.setUserNickname(userInfo.getNickname());
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WritingBean> getWritingList(int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl());
		//判断排序方式
		List<Posts> postsList = null;
		if (DaoEnum.ORDER_BY_TIME.equals(order)) {
			postsList = choose.getByTime(conn, partition);
		} else if (DaoEnum.ORDER_BY_LIKE.equals(order)){
			postsList = choose.getByLike(conn, partition);
		}
		if (postsList == null) {
			throw new Exception("获取列表结果为null");
		}

		UserDao userDao = DaoFactory.getUserDAO();
		List<WritingBean> beanList = new ArrayList<>();
		for (Posts posts : postsList) {
			User reviewerInfo = userDao.getImgAndNicknameById(conn, posts.getAuthorId());
			WritingBean wb = new WritingBean();
			//用户头像 使用base64转码
			byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);

			wb.setWriting(posts);
			wb.setUserNickname(reviewerInfo.getNickname());
			wb.setUserImg(imgByBase64);
			beanList.add(wb);
		}
		return beanList;
	}

	@Override
	public List<Posts> getSimpleWritingList(int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl());

		List<Posts> resList = null;
		if (DaoEnum.ORDER_BY_LIKE.equals(order)){
			resList = choose.getSimpleByLike(conn, partition);
		} else if (DaoEnum.ORDER_BY_TIME.equals(order)){
			resList = choose.getSimpleByTime(conn, partition);
		}
		return resList;
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
