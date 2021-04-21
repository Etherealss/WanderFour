package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.ReplyChoose;
import common.strategy.impl.comment.*;
import dao.CommentDao;
import dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.vo.CommentVo;
import pojo.bo.PageBo;
import pojo.dto.CommentDto;
import pojo.po.Comment;
import pojo.po.Writing;
import service.CommentService;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/23
 */
public class CommentServiceImpl implements CommentService {

    private Logger logger = Logger.getLogger(CommentServiceImpl.class);

    private static final String ORDER_BY_LIKE = "like";
    private static final String ORDER_BY_TIME = "time";

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    private String tableName;

    @Override
    public void setTableName(Class<? extends Writing> clazz) {
        // 文章评论表
        String articleCommentTableName = "`article_comment`";
        // 问贴评论表
        String postsCommentTableName = "`posts_comment`";
        if (clazz.equals(Article.class)) {
            tableName = articleCommentTableName;
        } else if (clazz.equals(Posts.class)) {
            tableName = postsCommentTableName;
        } else {
            try {
                throw new Exception("错误的评论表类型");
            } catch (Exception e) {
                logger.error("错误的评论表类型:clazz = " + clazz.getName() + ", e = " + e.getMessage());
            }
        }
    }

    @Override
    public PageBo<CommentDto> getHotCommentList(Long parentId, Long userId) {
        //选择策略 按点赞数获取热门评论及其回复
        CommentChoose choose = new CommentChoose(new GetHeadCommentsAndReplyByLike());
        //获取评论DtoList
        CommentVo vo = new CommentVo();
        vo.setParentId(parentId);
        vo.setUserid(userId);
        vo.setCommentDao(commentDao);
        vo.setUserDao(userDao);
        vo.setCommentTableName(tableName);
        //策略 获取所有评论数据
        List<CommentDto> list = choose.doGet(vo);

        // 包装Bean
        PageBo<CommentDto> pb = new PageBo<>(1, 3);
        pb.setList(list);
        //获取并存入总记录数
        Long totalCount = commentDao.countCommentByParentId(tableName, parentId);
        pb.setTotalCount(totalCount);

        return pb;
    }

    @Override
    public PageBo<CommentDto> getCommentListByPage
            (CommentVo vo, int currentPage) {
        vo.setCommentDao(commentDao);
        vo.setUserDao(userDao);
        vo.setCommentTableName(tableName);
        try {
            // 通过判断 获取评论或回复
            if (vo.getTargetId() == null) {
                // 获取评论
                return getCommentList(vo, currentPage);
            } else {
                // 获取回复
                return getReplyList(vo, currentPage);
            }
        } catch (Exception e) {
            logger.error("获取回复异常", e);
        }
        return null;
    }

    /**
     * 按分页获取页面所有评论
     * @see service.CommentService#getCommentListByPage(CommentVo, int)
     * @throws Exception
     */
    private PageBo<CommentDto> getCommentList(CommentVo vo, int currentPage) throws Exception {
        //要获取评论
        PageBo<CommentDto> pb;
        //TODO 此处按道理应该在策略中确定
        int commentRows = DaoEnum.COMMENT_ROWS_TEN;
        int replyRows = DaoEnum.REPLY_ROWS_THREE;
        vo.setCommentRows(commentRows);
        vo.setReplyRows(replyRows);
        Long parentId = vo.getParentId();
        //存入当前页码和每页显示的记录数
        pb = new PageBo<>(currentPage, commentRows);
        //获取并存入总记录数
        Long totalCount = commentDao.countCommentByParentId(tableName, parentId);
        pb.setTotalCount(totalCount);
        //计算索引 注意在 -1 的时候加入long类型，使结果升格为Long
        Long start = (currentPage - 1L) * commentRows;
        vo.setCommentStart(start);

        //策略选择
        CommentChoose choose;
        if (vo.getOrder().equals(DaoEnum.ORDER_BY_LIKE)) {
            //按点赞获取
            choose = new CommentChoose(new GetCommentsByLike());
        } else if (vo.getOrder().equals(DaoEnum.ORDER_BY_TIME)) {
            //按时间获取
            choose = new CommentChoose(new GetCommentsByTime());
        } else {
            throw new Exception("OrderBy参数错误：orberBy = " + vo.getOrder());
        }
        //执行策略
        List<CommentDto> list = choose.doGet(vo);
        pb.setList(list);
			/*
			计算总页码数
			如果总记录数可以整除以每页显示的记录数，
			那么总页数就是它们的商否则
			说明有几条数据要另开一页显示，总页数+1
			 */
        int page = (int) (totalCount / commentRows);
        int totalPage = totalCount % commentRows == 0 ? page : page + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 按分页获取页面所有回复
     * @see service.CommentService#getCommentListByPage(CommentVo, int)
     * @throws Exception
     */
    private PageBo<CommentDto> getReplyList(CommentVo vo, int currentPage) throws Exception {
        //TODO 此处按道理应该在策略中确定
        int replyRows = DaoEnum.REPLY_ROWS_TEN;
        vo.setReplyRows(replyRows);
        Long parentId = vo.getParentId();
        Long targetId = vo.getTargetId();
        //存入当前页码和每页显示的记录数
        PageBo<CommentDto> pb = new PageBo<>(currentPage, replyRows);

        //获取并存入总回复记录数
        Long totalCount = commentDao.countReplyByParentId(tableName, parentId);
        pb.setTotalCount(totalCount);

        //计算索引 注意在 -1 的时候加入long类型，使结果升格为Long
        Long start = (currentPage - 1L) * replyRows;
        vo.setReplyStart(start);
        logger.debug("获取回复，vo = " + vo);

        //策略选择
        ReplyChoose choose;
        if (vo.getOrder().equals(DaoEnum.ORDER_BY_LIKE)) {
            //按点赞获取
            choose = new ReplyChoose(new GetReplysByLike());
        } else if (vo.getOrder().equals(DaoEnum.ORDER_BY_TIME)) {
            //按时间获取
            choose = new ReplyChoose(new GetReplysByTime());
        } else {
            throw new Exception("OrderBy参数错误：orberBy = " + vo.getOrder());
        }
        //执行策略
        List<CommentDto> list = choose.doGet(vo);
        pb.setList(list);
			/*
			计算总页码数
			如果总记录数可以整除以每页显示的记录数，
			那么总页数就是它们的商否则
			说明有几条数据要另开一页显示，总页数+1
			 */
        int page = (int) (totalCount / replyRows);
        int totalPage = totalCount % replyRows == 0 ? page : page + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public ResultType publishNewComment(Comment comment) {
        boolean success;
        if (comment.getTargetId() == null) {
            //评论
            commentDao.insertNewComment(tableName, comment);
        } else {
            //回复
            commentDao.insertNewReply(tableName, comment);
        }
        return ResultType.SUCCESS;
    }

    @Override
    public ResultType deleteComment(Long commentId, Long userid) {
        int result = commentDao.deleteComment(tableName, commentId, userid);
        return result >= 1 ? ResultType.SUCCESS : ResultType.NOT_AUTHOR;
    }

}
