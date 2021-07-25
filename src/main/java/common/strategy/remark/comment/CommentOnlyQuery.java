package common.strategy.remark.comment;

import dao.CommentDao;
import dao.UserDao;
import lombok.Getter;
import lombok.Setter;
import pojo.po.Comment;

import java.util.List;
import java.util.Objects;

/**
 * @author wtk
 * @description 获取评论，不附带回复
 * 需要指定order排序方式，按时间、按点赞数
 * @date 2021-07-24
 */
@Setter
@Getter
public class CommentOnlyQuery extends CommentQueryStrategy {

    public CommentOnlyQuery(UserDao userDao, long userid, CommentDao commentDao,
                            String tableName, String order) {
        super(userDao, userid, commentDao, tableName, order);
    }

    @Override
    protected void checkArguments() {
        super.checkArguments();
        logger.trace("子类检查参数");
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
        return false;
    }
}
