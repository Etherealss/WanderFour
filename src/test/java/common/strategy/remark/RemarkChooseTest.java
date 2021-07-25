package common.strategy.remark;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.strategy.remark.comment.CommentQueryWithReply;
import common.strategy.remark.comment.CommentQueryStrategy;
import common.strategy.remark.reply.SimpleReplyQuery;
import common.strategy.remark.reply.ReplyQueryStrategy;
import dao.CommentDao;
import dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pojo.dto.CommentDto;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class RemarkChooseTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    @Test
    public void testGetReplyData() throws Exception {
        long userId = 1L;
        long parentId = 51L;
        String tableName = "article_comment";
        ReplyQueryStrategy replyQueryStrategy = new SimpleReplyQuery(
                userDao, userId, commentDao, tableName, DaoEnum.FIELD_ORDER_BY_LIKE);
        replyQueryStrategy.setReplyRows(5);
        replyQueryStrategy.setReplyStart(0);
        RemarkChoose remarkChoose = new RemarkChoose(replyQueryStrategy);
        List<CommentDto> replyData = remarkChoose.getRemarkData(parentId);
//        logger.debug("{}", replyData);

        JSONObject json = new JSONObject();
        json.put("replyData", replyData);
        logger.debug(json.toJSONString());
    }

    @Test
    public void testGetCommentData() throws Exception {
        long userId = 1L;
        long parentId = 4L;

        String tableName = "article_comment";
        CommentQueryStrategy commentQueryStrategy = new CommentQueryWithReply(
                userDao, userId, commentDao, tableName, DaoEnum.FIELD_ORDER_BY_LIKE);
        commentQueryStrategy.setCommentRows(5);
        commentQueryStrategy.setCommentStart(0);
        RemarkChoose remarkChoose = new RemarkChoose(commentQueryStrategy);
        List<CommentDto> commentData = remarkChoose.getRemarkData(parentId);
//        logger.debug("{}", commentData);

        JSONObject json = new JSONObject();
        json.put("commentData", commentData);
        logger.debug(json.toJSONString());
    }
}