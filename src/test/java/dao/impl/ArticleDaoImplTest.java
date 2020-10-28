package dao.impl;

import common.enums.Partition;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ArticleDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private WritingDao<Article> dao = null;
	private Connection conn;

	@Before
	public void init() throws Exception {
		dao = DaoFactory.getArticleDao();
		conn = JdbcUtil.beginTransaction();
	}

	@After
	public void closeConn() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void selectLastInsert() throws SQLException {
		Long aLong = dao.getLastInsertId(conn).longValue();
		System.out.println(aLong);
	}

	@Test
	public void updateArticle() {
		Article writing = new Article();
		writing.setId(68L);
		writing.setPartition(Partition.LEARNING.val());
		writing.setAuthorId(1L);
		writing.setCategory(1);

		writing.setTitle("我是标题");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());

		try {
			//修改文章
			logger.debug(dao.updateWritingInfo(conn, writing));
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void selectArticleById() throws SQLException {
		System.out.println(dao.getWritingById(conn, 2L));
	}

	@Test
	public void countArticle() {
	}

	@Test
	public void getBlogListByPage() {
	}

	@Test
	public void getUserArticleCount() {
	}

	@Test
	public void selectLikeCount() throws SQLException {
		int count = dao.getLikeCount(conn, 1L);
		logger.debug(count);
	}

	@Test
	public void updateLikeCount() throws SQLException {
		dao.updateLikeCount(conn, 2L, 6);
	}


	@Test
	public void testGetByTime() throws Exception {
		List<Article> writingListByLike = dao.getWritingListByOrder(
				conn, 1, "time", 0L, 5);
		for (Article article : writingListByLike) {
			logger.debug(article);
		}
	}
}