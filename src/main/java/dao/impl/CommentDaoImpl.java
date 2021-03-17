package dao.impl;

import common.util.JdbcUtil;
import dao.CommentDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;
import pojo.po.Writing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 评论/回复DAO
 * @date 2020/10/22
 */
public class CommentDaoImpl extends BaseDaoImpl implements CommentDao {

	/** 指定数据库表 */
	private final String COMMENT_TABLE;
	Connection conn = JdbcUtil.getConnection();

	/**
	 * @param clazz 确定评论表
	 * @throws Exception
	 */
	public CommentDaoImpl(Class<? extends Writing> clazz) throws Exception {
		// 文章评论表
		String articleCommentTableName = "`article_comment`";
		// 问贴评论表
		String postsCommentTableName = "`posts_comment`";
		if (clazz.equals(Article.class)) {
			COMMENT_TABLE = articleCommentTableName;
		} else if (clazz.equals(Posts.class)) {
			COMMENT_TABLE = postsCommentTableName;
		} else {
			throw new Exception("错误的评论表类型");
		}
	}

	/**
	 * 获取评论者的id
	 * @param commentId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Long getCommentUserId(Long commentId) throws SQLException {
		String sql = "SELECT `user_id` FROM " + COMMENT_TABLE + " WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<Long>(), commentId);
	}

	@Override
	public void insertNewComment(Comment comment) throws SQLException {
		String sql = "INSERT INTO " + COMMENT_TABLE + " (`user_id`,`parent_id`,`content`) VALUES(?,?,?);";
		Object[] params = {comment.getUserid(), comment.getParentId(), comment.getContent()};
		qr.update(conn, sql, params);
	}

	@Override
	public void insertNewReply(Comment comment) throws SQLException {
		String sql = "INSERT INTO " + COMMENT_TABLE + " (`user_id`,`parent_id`,`target_id`,`content`) VALUES(?,?,?,?);";
		Object[] params = {
				comment.getUserid(), comment.getParentId(), comment.getTargetId(), comment.getContent()
		};
		qr.update(conn, sql, params);
	}

	@Override
	public void deleteComment(Long id) throws SQLException {
		String sql = "DELETE FROM " + COMMENT_TABLE + " WHERE `id`=?";
		qr.update(conn, sql, id);
	}

	@Override
	public Comment getComment(Long id) throws SQLException {
		String sql = "SELECT `id`,`user_id` `userId`,`parent_id` `parentId`,`target_id` `targetId`," +
				"`content`,`liked`,`create_time` `createTime`,`state` " +
				" FROM " + COMMENT_TABLE + " WHERE id=?";
		return qr.query(conn, sql, new BeanHandler<>(Comment.class), id);
	}

	@Override
	public List<Comment> getCommentList(String orderByType, Long start, int rows, Long parentId) throws SQLException {
		String sql = "SELECT `id`,`user_id` `userId`,`parent_id` `parentId`, `target_id` `targetId`," +
				"`content`,`liked`,`create_time` `createTime`,`state` FROM " + COMMENT_TABLE +
				" WHERE `parent_id`=? AND `target_id` IS NOT NULL ORDER BY " + orderByType + " DESC LIMIT ?,?";
		Object[] params = {parentId, start, rows};
		List<Comment> list = qr.query(conn, sql, new BeanListHandler<>(Comment.class), params);
		return list;
	}

	@Override
	public List<Comment> getReplyList(String orderByType, Long start, int rows, Long parentId) throws SQLException {
		String sql = "SELECT `id`,`user_id` `userId`,`parent_id` `parentId`, `target_id` `targetId`," +
				"`content`,`liked`,`create_time` `createTime`,`state` FROM " + COMMENT_TABLE +
				" WHERE `parent_id`=? AND `target_id` IS NOT NULL ORDER BY " + orderByType + " DESC LIMIT ?,?";
		Object[] params = {parentId, start, rows};
		List<Comment> list = qr.query(conn, sql, new BeanListHandler<>(Comment.class), params);
		return list;
	}

	@Override
	public Long countCommentByParentId(Long parentId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + COMMENT_TABLE + " WHERE `parent_id`=? AND ISNULL(`target_id`);";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), parentId);
		return count;
	}

	@Override
	public Long countReplyByParentId(Long parentId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + COMMENT_TABLE + " WHERE `parent_id`=? AND `target_id` IS NOT NULL;";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), parentId);
		return count;
	}

	@Override
	public Long countReplyByTargetId(Long parentId, Long targetId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + COMMENT_TABLE + " WHERE `parent_id`=? AND `target_id`=?;";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), parentId, targetId);
		return count;
	}

}
