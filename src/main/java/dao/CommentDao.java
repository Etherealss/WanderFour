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
	 * @param commentId
	 * @return
	 * @throws SQLException
	 */
	Long getCommentUserId(Long commentId) throws SQLException;

	/**
	 * 发表评论
	 * @param comment
	 * @throws SQLException
	 */
	void insertNewComment(Comment comment) throws SQLException;

	/**
	 * 做出回复
	 * @param comment
	 * @throws SQLException
	 */
	void insertNewReply(Comment comment) throws SQLException;

	/**
	 * 删除评论或回复
	 * @param id
	 * @throws SQLException
	 */
	void deleteComment(Long id) throws SQLException;

	/**
	 * 通过id获取评论
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Comment getComment(Long id) throws SQLException;

	/**
	 * 获取 评论 记录
	 * @param orderByType 按时间 / 按点赞数 获取
	 * @param start
	 * @param rows 获取的记录行数
	 * @param parentId 评论所在的父容器
	 * @return
	 * @throws SQLException
	 */
	List<Comment> getCommentList(@Param("orderByType") String orderByType, @Param("start") Long start,
	                             @Param("rows") int rows, @Param("parentId") Long parentId) throws SQLException;

	/**
	 * 获取评论的 回复 记录
	 * @param orderByType 按时间 / 按点赞数 获取
	 * @param start
	 * @param rows
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	List<Comment> getReplyList(@Param("orderByType") String orderByType, @Param("start") Long start,
	                           @Param("rows") int rows, @Param("parentId") Long parentId) throws SQLException;

	/**
	 * 获取作品的评论总数
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	Long countCommentByParentId(Long parentId) throws SQLException;

	/**
	 * 获取作品的回复总数
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	Long countReplyByParentId(Long parentId) throws SQLException;

	/**
	 * 获取评论的回复总数
	 * @param parentId
	 * @param targetId
	 * @return
	 * @throws SQLException
	 */
	Long countReplyByTargetId(@Param("parentId") Long parentId, @Param("targetId") Long targetId) throws SQLException;
}
