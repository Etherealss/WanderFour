package service.impl;

import common.enums.ResultState;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Article;
import service.WritingService;

import java.sql.Connection;
import java.util.Date;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class ArticleServiceImpl implements WritingService<Article> {

	/** 子类的泛型类 */
	private Logger logger = Logger.getLogger(ArticleServiceImpl.class);
	private WritingDao<Article> dao = DaoFactory.getArticleDao();

	@Override
	public ResultState publishNewWriting(Article article) {
		logger.trace("发表新文章");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			//设置时间
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			logger.debug(article);
			// 查找合适的id
			Long maxId = dao.selectMaxWritingId(conn) + 1L;
			article.setId(maxId);
			boolean b1 = dao.updateNewWritingInfo(conn, article);
			boolean b2 = dao.updateNewWritingContent(conn, maxId, article.getContent());
			// 同时没有异常返回
			if (b1 && b2) {
				return ResultState.SUCCESS;
			} else {
				throw new Exception("发表新文章异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}

	@Override
	public Article getWriting(Long id) {
		logger.trace("获取文章");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			Article article = dao.selectWritingById(conn, id);
			article.setContent(dao.selectWritingContent(conn, id));
			return article;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultState updateWriting(Article article) {
		logger.trace("修改文章");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			// 检查修改文章的用户是否为作者
			if (article.getAuthorId().equals(dao.getAuthorByWritingId(conn, article.getId()))) {
				boolean b1 = dao.updateWritingInfo(conn, article);
				boolean b2 = dao.updateWritingContent(conn, article.getId(), article.getContent());
				if (b1 && b2) {
					return ResultState.SUCCESS;
				} else {
					throw new Exception("修改文章异常");
				}
			} else {
				return ResultState.NOT_AUTHOR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}

	@Override
	public ResultState deleteWriting(Long writingId, String deleterId) {
		logger.trace("删除文章");
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			if (deleterId.equals(dao.getAuthorByWritingId(conn, writingId))) {
				boolean b = dao.deleteWritingById(conn, writingId);
				if (b) {
					return ResultState.SUCCESS;
				} else {
					throw new Exception("删除文章异常");
				}
			} else {
				return ResultState.NOT_AUTHOR;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}

}
