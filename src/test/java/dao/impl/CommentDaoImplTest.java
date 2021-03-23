package dao.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.TestUtil;
import dao.CommentDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.Article;
import pojo.po.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class CommentDaoImplTest {
	private final Logger logger = Logger.getLogger(CommentDaoImplTest.class);
	@Autowired
	private CommentDao dao;

	private static final String ORDER_BY_LIKE = "`liked`";
	private static final String ORDER_BY_TIME = "`create_time`";

	private final String COMMENT_TABLE = "`posts_comment`";


	@Test
	public void testUserId() throws Exception {
		Long commentUserId = dao.getCommentUserId(COMMENT_TABLE, 1L);
		logger.debug(commentUserId);
	}

	@Test
	public void createNewComment() throws SQLException {
		Comment defaultCommentPo = TestUtil.getDefaultCommentPo();
		dao.insertNewComment(COMMENT_TABLE, defaultCommentPo);
	}

	@Test
	public void createNewReply() throws SQLException {
		Comment defaultCommentPo = TestUtil.getDefaultReply();
		dao.insertNewReply(COMMENT_TABLE, defaultCommentPo);
	}

	@Test
	public void deleteComment() throws SQLException {
		dao.deleteComment(COMMENT_TABLE, 9L);
	}

	@Test
	public void getComment() throws SQLException {
		logger.debug(dao.getComment(COMMENT_TABLE, 2L));
	}

	@Test
	public void getCommentList() throws SQLException {
		List<Comment> list = dao.getCommentList(COMMENT_TABLE, ORDER_BY_LIKE, 0L, 3, 1L);
		logger.debug(list);
	}

	@Test
	public void getReplyList() throws SQLException {
		List<Comment> list = dao.getReplyList(COMMENT_TABLE, ORDER_BY_LIKE, 0L, 3, 1L);
		logger.debug(list);
	}
}