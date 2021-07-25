package common.strategy.remark.comment;

import common.enums.DaoEnum;
import common.strategy.remark.RemarkChoose;
import common.strategy.remark.reply.SimpleReplyQuery;
import common.strategy.remark.reply.ReplyQueryStrategy;
import dao.CommentDao;
import dao.UserDao;
import lombok.Getter;
import lombok.Setter;
import pojo.po.Comment;

import java.util.List;
import java.util.Objects;

/**
 * @author wtk
 * @description 获取评论，附带回复，不添加回复引用
 * 需要指定order排序方式，按时间、按点赞数
 * @date 2021-07-24
 */
@Setter
@Getter
public class CommentQueryWithReply extends CommentQueryStrategy{

    /**
     * 查询评论的回复时需要用到
     */
    private ReplyQueryStrategy replyQuery;

    public CommentQueryWithReply(UserDao userDao, long userid, CommentDao commentDao,
                                 String tableName, String order) {
        super(userDao, userid, commentDao, tableName, order);
        initReplyQuery();
    }

    @Override
    protected void initReplyQuery() {
        // 设置评论的回复获取方式
        replyQuery = new SimpleReplyQuery(userDao, userid, commentDao,
                tableName, DaoEnum.FIELD_ORDER_BY_LIKE);
        replyQuery.setAddReference(false);
        replyQuery.setReplyRows(3);
        replyQuery.setReplyStart(0);
        remarkChoose = new RemarkChoose(replyQuery);
    }

    @Override
    protected List<Comment> getCommentList(long parentId) {
        return commentDao.getCommentList(
                tableName, order, commentStart, commentRows, parentId);
    }

    @Override
    public int countReplyByCommentId(long commentId) {
        return commentDao.countReplyByParentId(tableName, commentId);
    }

    @Override
    protected boolean isRequireReplys() {
        // 需要获取回复
        return true;
    }
}
