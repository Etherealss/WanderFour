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
	private WritingDao<Article> dao = DaoFactory.getArticleDao();

	@Override
	public Long publishNewWriting(Article article) throws Exception {
		logger.trace("发表新文章");
		Connection conn = JdbcUtil.getConnection();
		logger.debug(article);

		//添加文章信息
		boolean b1 = dao.createWritingInfo(conn, article);
		//获取自增的主键Id
		Long maxId = dao.getLastInsertId(conn).longValue();
		logger.debug("maxId = " + maxId);
		article.setId(maxId);
		//添加文章内容
		boolean b2 = dao.createWritingContent(conn, maxId, article.getContent());

		if (b1 && b2) {
			// 两次操作均无异常时返回
			return maxId;
		} else {
			throw new Exception("发表新文章异常");
		}
	}

	@Override
	public Article getWriting(Long id) throws Exception {
		logger.trace("获取文章");
		Connection conn = JdbcUtil.getConnection();
		Article article = dao.getWritingById(conn, id);
		article.setContent(dao.getWritingContent(conn, id));
		return article;
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
	public ResultType updateWriting(Article article) throws Exception {
		logger.trace("修改文章");
		Connection conn = JdbcUtil.getConnection();
		// 检查修改文章的用户是否为作者
		if (article.getAuthorId().equals(dao.getAuthorByWritingId(conn, article.getId()))) {
			boolean b1 = dao.updateWritingInfo(conn, article);
			boolean b2 = dao.updateWritingContent(conn, article.getId(), article.getContent());
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
		if (deleterId.equals(dao.getAuthorByWritingId(conn, writingId))) {
			//如果是，执行删除操作
			boolean b = dao.deleteWritingById(conn, writingId);
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
