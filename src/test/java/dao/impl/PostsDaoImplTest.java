package dao.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.TestUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.Posts;

import java.sql.Connection;
import java.sql.SQLException;

public class PostsDaoImplTest {

	private final Logger logger = Logger.getLogger(PostsDaoImplTest.class);
	private WritingDao<Posts> dao = null;
	private Connection conn;

	@Before
	public void setUp() throws Exception {
		dao = DaoFactory.getPostsDao();
		try {
			// 初始化数据连接
			conn = JdbcUtil.getConnection();
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void updateNewWritingInfo() throws SQLException {
		Posts posts = TestUtil.getDefaultPostsPo();
		boolean b = dao.updateNewWritingInfo(conn, posts);
		logger.debug("发表新帖子：" + b);
	}


	@Test
	public void updateWritingInfo() throws SQLException {
		Posts posts = TestUtil.getDefaultPostsPo();
		posts.setId(2L);
		posts.setContent("修改内容");
		boolean b = dao.updateWritingInfo(conn, posts);
		logger.debug("修改问贴：" + b);

	}

	@Test
	public void selectWritingById() throws SQLException {
		Posts posts = dao.selectWritingById(conn, 2L);
		logger.debug(posts);
	}

	@Test
	public void deleteWritingById() throws SQLException {
		boolean b = dao.deleteWritingById(conn, 3L);
		logger.debug("删除问贴：" + b);
	}

	@Test
	public void countWriting() {
	}

	@Test
	public void getWritingListByPage() {
	}

	@Test
	public void getUserWritingCount() {
	}

	@Test
	public void getAuthorByWritingId() throws SQLException {
		Long authorByWritingId = dao.getAuthorByWritingId(conn, 2L);
		logger.debug("问贴作者：" + authorByWritingId);
	}
}