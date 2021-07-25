package common.strategy.remark.reply;

import common.enums.DaoEnum;
import dao.CommentDao;
import dao.UserDao;
import lombok.Getter;
import lombok.Setter;
import pojo.po.Comment;

import java.util.List;
import java.util.Objects;

/**
 * @author wtk
 * @description
 * @date 2021-07-24
 */
@Setter
@Getter
public class SimpleReplyQuery extends ReplyQueryStrategy {

    public SimpleReplyQuery(UserDao userDao, long userid, CommentDao commentDao,
                            String tableName, String order) {
        super(userDao, userid, commentDao, tableName);
        this.order = order;
    }

    public SimpleReplyQuery(UserDao userDao, long userid, CommentDao commentDao,
                            String tableName, boolean isAddReference, String order) {
        super(userDao, userid, commentDao, tableName, isAddReference, order);
    }

    public SimpleReplyQuery(UserDao userDao, long userid, CommentDao commentDao, String tableName) {
        super(userDao, userid, commentDao, tableName);
    }

    @Override
    protected List<Comment> getPageReply(long commentId) {
        return commentDao.getReplyList(
                tableName, order, replyStart, replyRows, commentId);
    }

    @Override
    protected Comment getReply(long targetId) {
        return commentDao.getComment(tableName, targetId);
    }
}
