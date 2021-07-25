package dao.impl;

import common.util.TestUtil;
import dao.CommentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pojo.po.Comment;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class CommentDaoImplTest {
    private Logger logger = LoggerFactory.getLogger("testLogger");
    @Autowired
    private CommentDao dao;

    private static final String ORDER_BY_LIKE = "`liked`";
    private static final String ORDER_BY_TIME = "`create_time`";

    private final String COMMENT_TABLE = "`posts_comment`";


    @Test
    public void testUserId() throws Exception {
        Long commentUserId = dao.getCommentUserId(COMMENT_TABLE, 1L);
        logger.debug(String.valueOf(commentUserId));
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
        dao.deleteComment(COMMENT_TABLE, 9L, 123123L);
    }

    @Test
    public void getComment() throws SQLException {
        logger.debug(dao.getComment(COMMENT_TABLE, 2L).toString());
    }

    @Test
    public void getCommentList() throws SQLException {
        List<Comment> list = dao.getCommentList(COMMENT_TABLE, ORDER_BY_LIKE, 0L, 3, 1L);
        logger.debug(list.toString());
    }

    @Test
    public void getReplyList() throws SQLException {
        List<Comment> list = dao.getReplyList(COMMENT_TABLE, ORDER_BY_LIKE, 0L, 3, 1L);
        logger.info(list.toString());
    }
}