package common.strategy.remark.comment;

import common.strategy.remark.RemarkChoose;
import common.strategy.remark.RemarkQueryStrategy;
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
public abstract class CommentQueryStrategy extends RemarkQueryStrategy {
    /*
    本来CommentDao和tableName想放在实现类，方便日后扩展功能，
    毕竟对于评论功能来说，Comment表不止一个
    但这样一来，在使用上会复杂不少
    所以暂且放在父类
     */

    @Setter @Getter
    protected CommentDao commentDao;
    @Setter @Getter
    protected String tableName;
    @Setter @Getter
    protected long commentStart;
    @Setter @Getter
    protected int commentRows;
    /**
     * 排序方式，如按时间、按点赞数
     */
    @Setter @Getter
    protected String order;

    /**
     * 嵌套使用了获取回复的策略模式
     */
    protected RemarkChoose remarkChoose;

    public CommentQueryStrategy(UserDao userDao, long userid, CommentDao commentDao,
                                String tableName, String order) {
        super(userDao, userid);
        this.commentDao = commentDao;
        this.tableName = tableName;
        this.order = order;
    }

    @Override
    protected void checkArguments() {
        super.checkArguments();
        Objects.requireNonNull(commentDao, "CommentDao对象缺失！");
        Objects.requireNonNull(tableName, "tableName参数缺失！");
        Objects.requireNonNull(order, "查询评论的order参数缺失");
        if (commentRows <= 0) {
            throw new IllegalArgumentException("commentRows小于等于0！");
        }
    }

    /**
     * 获取评论记录
     * @return
     * @param parentId
     */
    protected abstract List<Comment> getCommentList(long parentId);

    /**
     * 是否获取评论的回复
     * @return
     */
    protected abstract boolean isRequireReplys();

    /**
     * 初始化评论的回复查询策略，需要获取评论回复时，可以在子类中指定回复的获取方式
     */
    protected void initReplyQuery() {
        throw new UnsupportedOperationException("当前策略不支持评论的回复查询");
    }

    /**
     * 通过评论id获取其回复列表（按点赞数获取3条记录）
     * @return
     */
    protected List<CommentBean> getReplys4Comment(long commentId) {
        // 获取该评论的回复
        List<CommentDto> replyData = remarkChoose.getRemarkData(commentId);

        // 从 List<CommentDto> 中分离出 List<CommentBean>
        List<CommentBean> replyBean = new ArrayList<>(replyData.size());
        for (CommentDto replyDatum : replyData) {
            replyBean.add(replyDatum.getParentComment());
        }
        return replyBean;
    }

    /**
     * 获取评论的回复数
     * @param commentId
     * @return
     */
    protected abstract int countReplyByCommentId(long commentId);

    /**
     * 获取评论的记录数据
     * 模板方法模式中的模板方法
     * @return
     * @param parentId
     */
    @Override
    public List<CommentDto> getRemarkData(long parentId) {
        // 检查参数
        this.checkArguments();

        //获取rows条 顶层评论 数据
        List<Comment> commentList = this.getCommentList(parentId);

		/*
		加工评论数据：
		1、封装CommentBean，包括评论信息、评论用户昵称和头像
		2、封装回复的评论信息
			2.1封装回复的评论的CommentBean
		3、判断是否要添加引用
			2.1、需要添加引用：获取并添加引用targetBean
			2.2、不需要添加，则进行下一步
		3、将commentBean, [replysCommentBean]，[targetBean]封装到Dto，添加到要返回的list中
		 */
        List<CommentBean> commentBeanList = new ArrayList<>(commentList.size());
        for (Comment comment : commentList) {
            Long targetId = comment.getTargetId();

			/*
			包装 顶层评论的Bean
			 */
            CommentBean commentBean = this.wrapCommentBean(comment, userid);
            commentBeanList.add(commentBean);
        }

        List<CommentDto> commentDtoList = new ArrayList<>(commentBeanList.size());
        //判断评论数据下是否需要附带回复
        if (this.isRequireReplys()) {
            for (CommentBean commentBean : commentBeanList) {
                Comment comment = commentBean.getComment();
                Long commentId = comment.getId();
				/*
				要获取该评论的回复记录信息列表
				*/
                // 通过评论id获取其回复列表（按点赞数获取3条记录）
                List<CommentBean> replys4Comment = this.getReplys4Comment(commentId);

                // 获取回复总数
                int count = this.countReplyByCommentId(commentId);
                //创建评论的Dto，封装数据
                CommentDto resultDto = new CommentDto(commentBean, replys4Comment, count);
                commentDtoList.add(resultDto);
            }
            return commentDtoList;
        }

        for (CommentBean commentBean : commentBeanList) {
            //创建评论的Dto，封装数据
            CommentDto resultDto = new CommentDto(commentBean, null, 0);
            commentDtoList.add(resultDto);
        }

        return commentDtoList;
    }


}
