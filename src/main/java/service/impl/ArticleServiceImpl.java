package service.impl;

import common.dto.ResultState;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import pojo.po.Article;
import pojo.po.Writing;
import service.WritingService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;

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
			dao.updateNewArticle(conn, article);
			return ResultState.SUCCESS;
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
				return dao.selectArticleById(conn, id);
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
			dao.updateArticle(conn, article);
			return ResultState.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}

	@Override
	public ResultState deleteWriting(Long id) {
		logger.trace("删除文章");
		return null;
	}

}
