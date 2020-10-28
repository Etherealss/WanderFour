package dao;

import pojo.po.Comment;

import java.sql.Connection;
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
	 * @param conn
	 * @param commentId
	 * @return
	 * @throws SQLException
	 */
	Long getCommentUserId(Connection conn, Long commentId) throws SQLException;

	/**
	 * 发表评论
	 * @param conn
	 * @param comment
	 * @return
	 * @throws SQLException
	 */
	boolean createNewComment(Connection conn, Comment comment) throws SQLException;

	/**
	 * 做出回复
	 * @param conn
	 * @param comment
	 * @return
	 * @throws SQLException
	 */
	boolean createNewReply(Connection conn, Comment comment) throws SQLException;

	/**
	 * 删除评论或回复
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean deleteComment(Connection conn, Long id) throws SQLException;

	/**
	 * 通过id获取评论
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Comment getComment(Connection conn, Long id) throws SQLException;

	/**
	 * 获取 评论 记录
	 * @param conn
	 * @param orderByType 按时间 / 按点赞数 获取
	 * @param start
	 * @param rows 获取的记录行数
	 * @param parentId 评论所在的父容器
	 * @return
	 * @throws SQLException
	 */
	List<Comment> getCommentList(Connection conn, String orderByType, Long start, int rows, Long parentId) throws SQLException;

	/**
	 * 获取评论的 回复 记录
	 * @param conn
	 * @param orderByType 按时间 / 按点赞数 获取
	 * @param start
	 * @param rows
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	List<Comment> getReplyList(Connection conn, String orderByType, Long start, int rows, Long parentId) throws SQLException;

	/**
	 * 获取作品的评论总数
	 * @param conn
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	Long countCommentByParentId(Connection conn, Long parentId) throws SQLException;

	/**
	 * 获取作品的回复总数
	 * @param conn
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	Long countReplyByParentId(Connection conn, Long parentId) throws SQLException;

	/**
	 * 获取评论的回复总数
	 * @param conn
	 * @param parentId
	 * @param targetId
	 * @return
	 * @throws SQLException
	 */
	Long countReplyByTargetId(Connection conn, Long parentId, Long targetId) throws SQLException;
}
