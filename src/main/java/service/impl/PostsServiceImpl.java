package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.enums.WritingType;
import common.factory.DaoFactory;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetPostsStrategyImpl;
import common.strategy.impl.comment.GetOnlyCommentByLike;
import common.util.FileUtil;
import common.util.JdbcUtil;
import dao.UserDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.vo.CommentVo;
import pojo.bean.WritingBean;
import pojo.bo.EsBo;
import pojo.dto.CommentDto;
import pojo.dto.WritingDto;
import pojo.po.Posts;
import pojo.po.User;
import service.WritingService;

import java.sql.Connection;
import java.sql.SQLException;
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
	@Autowired
	private UserDao userDao;

	@Override
	public Long publishNewWriting(Posts posts) throws Exception {
		logger.trace("发表新问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			logger.debug(posts);

			//添加问贴
			dao.createWritingInfo(posts);
			Long maxId = posts.getId();
			logger.debug("maxId = " + maxId);
			return maxId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public WritingBean<Posts> getWritingBean(Long writingId, Long userid) {
		logger.trace("获取问贴");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			Posts posts = dao.getWritingById(writingId);

			User userInfo = userDao.getImgAndNicknameById(posts.getAuthorId());
			byte[] imgStream = FileUtil.getFileStream(userInfo.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);

			WritingBean<Posts> bean = new WritingBean<>();
			bean.setUserImg(imgByBase64);
			bean.setWriting(posts);
			bean.setUserNickname(userInfo.getNickname());

			if (userid != null) {

			}

			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WritingDto<Posts>> getWritingList(Long userid, int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl());
		//判断排序方式
		List<Posts> postsList = null;
		if (DaoEnum.ORDER_BY_TIME.equals(order)) {
			postsList = choose.getByTime(conn, partition);
		} else if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
			postsList = choose.getByLike(conn, partition);
		}
		if (postsList == null) {
			throw new Exception("获取列表结果为null");
		}

		UserDao userDao = DaoFactory.getUserDAO();
		List<WritingDto<Posts>> wdList = new ArrayList<>();
		for (Posts posts : postsList) {

			WritingBean<Posts> bean = this.getWritingBeanWithAuthorInfo(conn, userDao, posts);

			//获取评论
			CommentChoose commentChoose = new CommentChoose(new GetOnlyCommentByLike());
			CommentVo commentVo = new CommentVo();
			commentVo.setConn(conn);
			commentVo.setDao(DaoFactory.getCommentDao(Posts.class));
			commentVo.setUserid(userid);
			//评论的问贴的id
			commentVo.setParentId(posts.getId());
			//获取多条评论的Dto数据
			List<CommentDto> commentDtos = commentChoose.doGet(commentVo);

			//封装问贴数据及其评论数据
			WritingDto<Posts> writingDto = new WritingDto<>();
			writingDto.setCommentDtoList(commentDtos);
			writingDto.setWritingBean(bean);

			wdList.add(writingDto);
		}
		return wdList;
	}

	private WritingBean<Posts> getWritingBeanWithAuthorInfo(Connection conn, UserDao userDao, Posts posts) throws SQLException {
		User reviewerInfo = userDao.getImgAndNicknameById(posts.getAuthorId());
		WritingBean<Posts> bean = new WritingBean<>();
		//用户头像 使用base64转码
		byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
		String imgByBase64 = FileUtil.getImgByBase64(imgStream);

		bean.setWriting(posts);
		bean.setUserNickname(reviewerInfo.getNickname());
		bean.setUserImg(imgByBase64);
		return bean;
	}

	@Override
	public List<Posts> getSimpleWritingList(int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl());

		List<Posts> resList = null;
		if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
			resList = choose.getSimpleByLike(conn, partition);
		} else if (DaoEnum.ORDER_BY_TIME.equals(order)) {
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
			if (posts.getAuthorId().equals(dao.getAuthorByWritingId(posts.getId()))) {
				dao.updateWritingInfo(posts);
				return ResultType.SUCCESS;
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
			if (deleterId.equals(dao.getAuthorByWritingId(writingId))) {
				//如果是，执行删除操作
				dao.deleteWritingById(writingId);
				return ResultType.SUCCESS;
			} else {
				return ResultType.NOT_AUTHOR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultType.EXCEPTION;
		}
	}

	@Override
	public List<Long> getAllWritingsId() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		WritingDao<Posts> postsDao = DaoFactory.getPostsDao();
		return postsDao.getAllWritingsId();
	}

	@Override
	public List<EsBo> getWritingListByIds(List<Long> ids) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		WritingDao<Posts> dao = DaoFactory.getPostsDao();
		List<EsBo> writings = null;
		if (ids.size() != 0) {
			writings = dao.getWritingsByIds(ids);

			for (EsBo esBo : writings) {
				esBo.setWritingType(WritingType.POSTS.val());
			}
		}
		return writings;
	}
}
