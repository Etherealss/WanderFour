package dao;

import org.apache.ibatis.annotations.Param;
import pojo.po.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/22
 */
public interface CommentDao {

    /**
     * 获取评论者的id
     * @param commentTableName
     * @param commentId
     * @return
     */
    Long getCommentUserId(@Param("commentTableName") String commentTableName,
                          @Param("commentId") Long commentId);

    /**
     * 发表评论
     * @param commentTableName
     * @param comment
     */
    void insertNewComment(@Param("commentTableName") String commentTableName,
                          @Param("comment") Comment comment);

    /**
     * 做出回复
     * @param commentTableName
     * @param comment
     */
    void insertNewReply(@Param("commentTableName") String commentTableName,
                        @Param("comment") Comment comment);

    /**
     * 删除评论或回复
     * @param commentTableName
     * @param id
     * @param userId
     * @return
     */
    int deleteComment(@Param("commentTableName") String commentTableName,
                      @Param("id") Long id, @Param("userId") Long userId);

    /**
     * 通过id获取评论
     * @param commentTableName
     * @param id
     * @return
     * @throws SQLException
     */
    Comment getComment(@Param("commentTableName") String commentTableName,
                       @Param("id") Long id);

    /**
     * 获取 评论 记录
     * @param commentTableName
     * @param orderByType      按时间 / 按点赞数 获取
     * @param start
     * @param rows             获取的记录行数
     * @param parentId         评论所在的父容器
     * @return
     */
    List<Comment> getCommentList(@Param("commentTableName") String commentTableName,
                                 @Param("orderByType") String orderByType, @Param("start") Long start,
                                 @Param("rows") int rows, @Param("parentId") Long parentId);

    /**
     * 获取评论的 回复 记录
     * @param commentTableName
     * @param orderByType      按时间 / 按点赞数 获取
     * @param start
     * @param rows
     * @param parentId
     * @return
     */
    List<Comment> getReplyList(@Param("commentTableName") String commentTableName,
                               @Param("orderByType") String orderByType, @Param("start") Long start,
                               @Param("rows") int rows, @Param("parentId") Long parentId);

    /**
     * 获取作品的评论总数
     * @param commentTableName
     * @param parentId
     * @return
     */
    Long countCommentByParentId(@Param("commentTableName") String commentTableName,
                                @Param("parentId") Long parentId);

    /**
     * 获取作品的回复总数
     * @param commentTableName
     * @param parentId
     * @return
     */
    Long countReplyByParentId(@Param("commentTableName") String commentTableName,
                              @Param("parentId") Long parentId);

    /**
     * 获取评论的回复总数
     * @param commentTableName
     * @param parentId
     * @param targetId
     * @return
     */
    Long countReplyByTargetId(@Param("commentTableName") String commentTableName,
                              @Param("parentId") Long parentId, @Param("targetId") Long targetId);
}
