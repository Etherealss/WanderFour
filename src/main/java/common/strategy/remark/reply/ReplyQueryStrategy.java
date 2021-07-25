package common.strategy.remark.reply;

import common.strategy.remark.RemarkQueryStrategy;
import common.util.CommentUtil;
import dao.CommentDao;
import dao.UserDao;
import lombok.Getter;
import lombok.Setter;
import pojo.bean.CommentBean;
import pojo.dto.CommentDto;
import pojo.po.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wtk
 * @description
 * @date 2021-07-23
 */
@Setter
@Getter
public abstract class ReplyQueryStrategy extends RemarkQueryStrategy {
    /*
    本来CommentDao和tableName想放在实现类，方便日后扩展功能，
    毕竟对于评论功能来说，Comment表不止一个
    但这样一来，在使用上会复杂不少
    所以暂且放在父类
     */

    protected CommentDao commentDao;
    protected String tableName;

    /**
     * 是否添加回复的引用
     * 本来是放在实现类，由不同的实现类分别指定的
     * 但是因为需要使用策略模式获取评论或回复的场景较为固定，定制的方案也较为固定，不必放在子类
     * 所以现在放在父类，由构造方法传参，让用户指定。
     */
    protected boolean isAddReference = true;
    protected long replyStart = 0;
    protected int replyRows;

    protected String order;

    public ReplyQueryStrategy(UserDao userDao, long userid, CommentDao commentDao,
                              String tableName, boolean isAddReference, String order) {
        super(userDao, userid);
        this.commentDao = commentDao;
        this.tableName = tableName;
        this.isAddReference = isAddReference;
        this.order = order;
    }

    public ReplyQueryStrategy(UserDao userDao, long userid, CommentDao commentDao, String tableName) {
        super(userDao, userid);
        this.commentDao = commentDao;
        this.tableName = tableName;
    }

    @Override
    protected void checkArguments() {
        super.checkArguments();
        Objects.requireNonNull(commentDao, "commentDao对象缺失");
        Objects.requireNonNull(tableName, "tableName对象缺失");
        Objects.requireNonNull(order, "order参数缺失");
        if (replyRows <= 0) {
            throw new IllegalArgumentException("replyRows参数小于等于0，异常");
        }

    }

    /**
     * 获取一整页的回复记录
     * @return
     * @param commentId
     */
    protected abstract List<Comment> getPageReply(long commentId);

    /**
     * 获取某一条回复记录数据
     * @param targetId
     * @return
     */
    protected abstract Comment getReply(long targetId);

    /**
     * 获取回复的记录数据
     * 模板方法模式中的模板方法
     * @return
     * @param parentId
     */
    @Override
    public List<CommentDto> getRemarkData(long parentId) {
        // 检查参数
        this.checkArguments();

        //获取rows条 顶层评论 数据
        List<Comment> replyList = this.getPageReply(parentId);

        List<CommentBean> replyBeanList = new ArrayList<>(replyList.size());
        for (Comment reply : replyList) {
			// 包装 回复记录对象
            CommentBean replyBean = this.wrapCommentBean(reply, userid);
            replyBeanList.add(replyBean);
        }

        // 当前要获取的是评论下的【回复列表】，需要判断是否添加回复引用
        // 结果列表
        List<CommentDto> returnDtoList = new ArrayList<>();
        if (isAddReference) {
            for (CommentBean replyBean : replyBeanList) {
                Comment reply = replyBean.getComment();
                Long targetId = reply.getTargetId();

                /*
                1、parentId和targetId不相同，说明该评论是回复其他评论
                则进入if语句，查询回复的评论，添加引用(reply)
                2、parentId和targetId相同，说明该回复是回复评论，不引用
                获取的targetComment为null，不影响封装
                 */
                CommentBean targetBean = null;
                // parentId和targetId不为null
                if (!reply.getParentId().equals(targetId)) {
                    //该回复是回复另一条回复，则查询被回复的评论，添加引用(reply)
                    //targetComment 意：复的那个记录的对象，用户添加引用

                    Comment targetComment = this.getReply(targetId);
                    targetBean = CommentUtil.getCommentBean(userDao, targetComment, userid);
                }
                // 回复的Dto，封装数据
                CommentDto resultDto = new CommentDto(replyBean, targetBean);
                returnDtoList.add(resultDto);
            }
        } else {
            // 不添加回复引用
            for (CommentBean replyBean : replyBeanList) {
                CommentDto resultDto = new CommentDto(replyBean, null);
                returnDtoList.add(resultDto);
            }
        }
        return returnDtoList;
    }


}
