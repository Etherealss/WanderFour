package service.impl;

import common.enums.*;
import common.factory.DaoFactory;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetArticleStrategyImpl;
import common.strategy.impl.comment.GetOnlyCommentByLike;
import common.util.FileUtil;
import common.util.JdbcUtil;
import dao.LikeDao;
import dao.UserDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.vo.CommentVo;
import pojo.bean.WritingBean;
import pojo.bo.EsBo;
import pojo.dto.CommentDto;
import pojo.dto.WritingDto;
import pojo.po.Article;
import pojo.po.LikeRecord;
import pojo.po.User;
import service.WritingService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class ArticleServiceImpl implements WritingService<Article> {

	private Logger logger = Logger.getLogger(ArticleServiceImpl.class);
	@Autowired
	private WritingDao<Article> writingDao;

	@Override
	public Long publishNewWriting(Article article) throws Exception {
		logger.trace("发表新文章");
		logger.debug(article);

		//添加文章信息
		writingDao.createWritingInfo(article);

		// MyBatis将新插入的id封装在pojo对象的字段中
		Long maxId = article.getId();
		logger.debug("maxId = " + maxId);
		article.setId(maxId);
		//添加文章内容
		writingDao.createWritingContent(maxId, article.getContent());

		return maxId;
	}

	@Override
	public WritingBean<Article> getWritingBean(Long writingId, Long userid) throws Exception {
		logger.trace("获取文章");
		Connection conn = JdbcUtil.getConnection();
		Article article = writingDao.getWritingById(writingId);
		if (article == null) {
			return null;
		}
		//获取文章信息包
		UserDao userDao = DaoFactory.getUserDAO();
		logger.debug(article);
		WritingBean<Article> bean = this.getWrticleBeanWithAuthorInfo(conn, userDao, article);

		//userid可能为null
		if (userid != null) {
			//判断是否为作者
			bean.setAuthor(article.getAuthorId().equals(userid));
			LikeDao likeDao = DaoFactory.getLikeDao(TargetType.ARTICLE);
			//判断是否已点赞
			LikeRecord likeRecord = new LikeRecord();
			likeRecord.setUserid(userid);
			likeRecord.setTargetType(TargetType.ARTICLE);
			likeRecord.setTargetId(writingId);
			boolean isLiked = likeDao.countUserLikeRecord(conn, likeRecord);

			//注意取反
			bean.setLiked(isLiked);

			//TODO 判断是否已收藏
		}

		return bean;
	}

	/**
	 * @param conn
	 * @param article
	 * @param userDao 因为可能会循环包装bean，所以dao在外部初始化
	 * @return
	 * @throws SQLException
	 */
	private WritingBean<Article> getWrticleBeanWithAuthorInfo(Connection conn, UserDao userDao, Article article) throws SQLException {
		//获取文章的内容
		String content = writingDao.getWritingContent(article.getId());
		article.setContent(content);

		//获取用户的信息
		User reviewerInfo = userDao.getImgAndNicknameById(article.getAuthorId());
		WritingBean<Article> bean = new WritingBean<>();
		//用户头像 使用base64转码
		byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
		String imgByBase64 = FileUtil.getImgByBase64(imgStream);

		//打包
		bean.setWriting(article);
		bean.setUserNickname(reviewerInfo.getNickname());
		//TODO 设置作品的作者头像数据
		bean.setUserImg(imgByBase64);
		return bean;
	}

	@Override
	public List<WritingDto<Article>> getWritingList(Long userid, int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Article> choose = new GetWritingListChoose<>(new GetArticleStrategyImpl());
		//判断排序方式
		List<Article> articleList = null;
		if (DaoEnum.ORDER_BY_TIME.equals(order)) {
			articleList = choose.getByTime(conn, partition);
		} else if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
			articleList = choose.getByLike(conn, partition);
		}
		if (articleList == null) {
			throw new Exception("获取列表结果为null");
		}


		List<WritingDto<Article>> wdList = new ArrayList<>();

		UserDao userDao = DaoFactory.getUserDAO();
		for (Article article : articleList) {
			WritingBean<Article> bean = this.getWrticleBeanWithAuthorInfo(conn, userDao, article);
			//获取评论
			CommentChoose commentChoose = new CommentChoose(new GetOnlyCommentByLike());
			CommentVo commentVo = new CommentVo();
			commentVo.setConn(conn);
			commentVo.setDao(DaoFactory.getCommentDao(Article.class));
			commentVo.setUserid(userid);
			//评论的文章的id
			commentVo.setParentId(article.getId());
			commentVo.setReplyRows(DaoEnum.ROWS_ZERO);
			//获取多条评论的Dto数据
			List<CommentDto> commentDtos = commentChoose.doGet(commentVo);

			//封装文章数据及其评论数据
			WritingDto<Article> writingDto = new WritingDto<>();
			writingDto.setCommentDtoList(commentDtos);
			writingDto.setWritingBean(bean);

			wdList.add(writingDto);
		}
		return wdList;
	}

	@Override
	public List<Article> getSimpleWritingList(int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Article> choose = new GetWritingListChoose<>(new GetArticleStrategyImpl());

		List<Article> resList = null;
		if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
			resList = choose.getSimpleByLike(conn, partition);
		} else if (DaoEnum.ORDER_BY_TIME.equals(order)) {
			resList = choose.getSimpleByTime(conn, partition);
		}
		return resList;
	}

	@Override
	public ResultType updateWriting(Article article) throws Exception {
		logger.trace("修改文章");
		Connection conn = JdbcUtil.getConnection();
		// 检查修改文章的用户是否为作者
		if (article.getAuthorId().equals(writingDao.getAuthorByWritingId(article.getId()))) {
			writingDao.updateWritingInfo(article);
			writingDao.updateWritingContent(article.getId(), article.getContent());
			return ResultType.SUCCESS;
		} else {
			return ResultType.NOT_AUTHOR;
		}
	}

	@Override
	public ResultType deleteWriting(Long writingId, Long deleterId) throws Exception {
		logger.trace("删除文章");
		Connection conn = JdbcUtil.getConnection();
		// 检查是否作者本人删除
		if (deleterId.equals(writingDao.getAuthorByWritingId(writingId))) {
			//如果是，执行删除操作
			writingDao.deleteWritingById(writingId);
			return ResultType.SUCCESS;
		} else {
			return ResultType.NOT_AUTHOR;
		}
	}

	@Override
	public List<Long> getAllWritingsId() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		WritingDao<Article> articleDao = DaoFactory.getArticleDao();
		return articleDao.getAllWritingsId();
	}

	@Override
	public List<EsBo> getWritingListByIds(List<Long> ids) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		WritingDao<Article> dao = DaoFactory.getArticleDao();
		List<EsBo> writings = null;
		if (ids.size() != 0) {
			writings = dao.getWritingsByIds(ids);
			for (EsBo esBo : writings) {
				esBo.setWritingType(WritingType.ARTICLE.val());
				esBo.setContent(dao.getWritingContent(esBo.getWritingId()));
			}
		}
		return writings;
	}

}
