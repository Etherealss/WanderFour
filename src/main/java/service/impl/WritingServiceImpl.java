package service.impl;

import common.dto.ResultState;
import common.util.JdbcUtil;
import dao.ArticleDao;
import dao.impl.ArticleDaoImpl;
import org.apache.log4j.Logger;
import pojo.Writing;
import service.WritingService;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class WritingServiceImpl<T extends Writing> implements WritingService<T> {

	/** 子类的泛型类 */
	private Logger logger = Logger.getLogger(WritingServiceImpl.class);
	private ArticleDao<T> dao = new ArticleDaoImpl<>();

	@Override
	public ResultState publishNewWriting(Writing writing) {
		logger.trace("发表新文章");
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			dao.updateNewArticle(conn, (T) writing);
			return ResultState.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}
}
