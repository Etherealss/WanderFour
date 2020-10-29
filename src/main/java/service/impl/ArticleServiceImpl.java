package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetArticleStrategyImpl;
import common.util.FileUtil;
import common.util.JdbcUtil;
import dao.UserDao;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.dto.WritingBean;
import pojo.po.Article;
import pojo.po.User;
import service.WritingService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class ArticleServiceImpl implements WritingService<Article> {

	private Logger logger = Logger.getLogger(ArticleServiceImpl.class);
	private WritingDao<Article> writingDao = DaoFactory.getArticleDao();

	@Override
	public Long publishNewWriting(Article article) throws Exception {
		logger.trace("发表新文章");
		Connection conn = JdbcUtil.getConnection();
		logger.debug(article);

		//添加文章信息
		boolean b1 = writingDao.createWritingInfo(conn, article);
		//获取自增的主键Id
		Long maxId = writingDao.getLastInsertId(conn).longValue();
		logger.debug("maxId = " + maxId);
		article.setId(maxId);
		//添加文章内容
		boolean b2 = writingDao.createWritingContent(conn, maxId, article.getContent());

		if (b1 && b2) {
			// 两次操作均无异常时返回
			return maxId;
		} else {
			throw new Exception("发表新文章异常");
		}
	}

	@Override
	public WritingBean<Article> getWriting(Long id) throws Exception {
		logger.trace("获取文章");
		Connection conn = JdbcUtil.getConnection();
		Article article = writingDao.getWritingById(conn, id);
		article.setContent(writingDao.getWritingContent(conn, id));
		WritingBean<Article> bean = new WritingBean<>();
		UserDao userDao = DaoFactory.getUserDAO();
		User userInfo = userDao.getImgAndNicknameById(conn, article.getAuthorId());

		bean.setWriting(article);
		byte[] imgStream = FileUtil.getFileStream(userInfo.getAvatarPath());
		String imgByBase64 = FileUtil.getImgByBase64(imgStream);
		bean.setUserImg(imgByBase64);
		bean.setUserNickname(userInfo.getNickname());
		return bean;
	}

	@Override
	public List<WritingBean> getWritingList(int partition, String order) throws Exception {
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

		UserDao userDao = DaoFactory.getUserDAO();
		List<WritingBean> beanList = new ArrayList<>();
		for (Article article : articleList) {
			//获取文章的内容
			String content = writingDao.getWritingContent(conn, article.getId());
			article.setContent(content);

			//获取用户的信息
			User reviewerInfo = userDao.getImgAndNicknameById(conn, article.getAuthorId());
			WritingBean wb = new WritingBean();
			//用户头像 使用base64转码
			byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);

			wb.setWriting(article);
			wb.setUserNickname(reviewerInfo.getNickname());
			wb.setUserImg(imgByBase64);
			beanList.add(wb);
		}
		return beanList;
	}

	@Override
	public List<Article> getSimpleWritingList(int partition, String order) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		GetWritingListChoose<Article> choose = new GetWritingListChoose<>(new GetArticleStrategyImpl());

		List<Article> resList = null;
		if (DaoEnum.ORDER_BY_LIKE.equals(order)){
			resList = choose.getSimpleByLike(conn, partition);
		} else if (DaoEnum.ORDER_BY_TIME.equals(order)){
			resList = choose.getSimpleByTime(conn, partition);
		}
		return resList;
	}

	@Override
	public ResultType updateWriting(Article article) throws Exception {
		logger.trace("修改文章");
		Connection conn = JdbcUtil.getConnection();
		// 检查修改文章的用户是否为作者
		if (article.getAuthorId().equals(writingDao.getAuthorByWritingId(conn, article.getId()))) {
			boolean b1 = writingDao.updateWritingInfo(conn, article);
			boolean b2 = writingDao.updateWritingContent(conn, article.getId(), article.getContent());
			if (b1) {
				if (b2) {
					return ResultType.SUCCESS;
				} else {
					throw new Exception("修改文章内容异常");
				}
			} else {
				throw new Exception("修改文章信息异常");
			}
		} else {
			return ResultType.NOT_AUTHOR;
		}
	}

	@Override
	public ResultType deleteWriting(Long writingId, Long deleterId) throws Exception {
		logger.trace("删除文章");
		Connection conn = JdbcUtil.getConnection();
		// 检查是否作者本人删除
		if (deleterId.equals(writingDao.getAuthorByWritingId(conn, writingId))) {
			//如果是，执行删除操作
			boolean b = writingDao.deleteWritingById(conn, writingId);
			if (b) {
				return ResultType.SUCCESS;
			} else {
				throw new Exception("删除文章异常");
			}
		} else {
			return ResultType.NOT_AUTHOR;
		}
	}

}
