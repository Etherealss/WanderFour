package dao.impl;

import common.enums.TargetType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.TestUtil;
import dao.CommentDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.Article;
import pojo.po.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommentDaoImplTest {
	private final Logger logger = Logger.getLogger(CommentDaoImplTest.class);
	private CommentDao dao;
	private Connection conn;

	private static final String ORDER_BY_LIKE = "`liked`";
	private static final String ORDER_BY_TIME = "`create_time`";
	@Before
	public void setUp() throws Exception {
		dao = DaoFactory.getCommentDao(Article.class);
		conn = JdbcUtil.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		JdbcUtil.closeTransaction();
	}


	@Test
	public void testUserId() throws Exception {
		Long commentUserId = dao.getCommentUserId(conn, 1L);
		logger.debug(commentUserId);
	}

	@Test
	public void createNewComment() throws SQLException {
		Comment defaultCommentPo = TestUtil.getDefaultCommentPo();
		logger.debug(dao.createNewComment(conn, defaultCommentPo));
	}

	@Test
	public void createNewReply() throws SQLException {
		Comment defaultCommentPo = TestUtil.getDefaultReply();
		logger.debug(dao.createNewReply(conn, defaultCommentPo));
	}

	@Test
	public void deleteComment() throws SQLException {
		logger.debug(dao.deleteComment(conn, 9L));
	}

	@Test
	public void getComment() throws SQLException {
		logger.debug(dao.getComment(conn, 2L));
	}

	@Test
	public void getCommentList() throws SQLException {
		List<Comment> list = dao.getCommentList(conn, ORDER_BY_LIKE, 0L, 3, 1L);
		logger.debug(list);
	}

	@Test
	public void getReplyList() throws SQLException {
		List<Comment> list = dao.getReplyList(conn, ORDER_BY_LIKE, 0L, 3, 1L);
		logger.debug(list);
	}
}