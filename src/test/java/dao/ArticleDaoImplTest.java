package dao;

import common.enums.Partition;
import common.factory.DaoFactory;
import common.factory.ServiceFactory;
import common.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.Article;
import service.WritingService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class ArticleDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private WritingDao<Article> dao = null;
	private Connection conn;
	@Before
	public void init() {
		dao = DaoFactory.getArticleDao();
		try {
			// 初始化数据连接
			conn = JdbcUtil.getConnection();
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
	}

	@After
	public void closeConn() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void updateNewArticle() {
	}

	@Test
	public void updateArticle() throws SQLException {
		Article writing = new Article();
		writing.setId(2L);
		writing.setPartition(Partition.LEARNING);
		writing.setAuthorId("1@qq.com");
		writing.setCategory("二级分类");

		writing.setTitle("我是标题");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());
		dao.updateArticle(conn, writing);
	}

	@Test
	public void selectArticleById() throws SQLException {
		System.out.println(dao.selectArticleById(conn, 2L));
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
}