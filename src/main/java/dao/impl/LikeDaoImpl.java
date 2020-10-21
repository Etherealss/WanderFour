package dao.impl;

import common.enums.TargetType;
import dao.LikeDao;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import pojo.po.LikeRecord;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description 点赞DAO
 * @date 2020/10/14
 */
public class LikeDaoImpl extends BaseDaoImpl implements LikeDao {
	private Logger logger = Logger.getLogger(LikeDaoImpl.class);

	/** 指定数据库表 */
	private final String LIKE_TABLE;

	/**
	 * @param type
	 */
	public LikeDaoImpl(TargetType type) {
		// 文章点赞表
		String likeArticle = "`article_like_record`";
		// 问贴点赞表
		String likePosts = "`posts_like_record`";
		// 评论点赞表
		String likeComment = "`posts_like_record`";
		switch (type) {
			case ARTICLE:
				LIKE_TABLE = likeArticle;
				break;
			case POSTS:
				LIKE_TABLE = likePosts;
				break;
			default:
				LIKE_TABLE = likeComment;
				break;
		}
	}

	@Override
	public boolean insertLikeRecord(Connection conn, LikeRecord record) throws SQLException {
		String sql = "INSERT INTO " + LIKE_TABLE + " (`user_id`, `target_id`) VALUES(?,?);";
		Object[] params = {
				record.getUserid(), record.getTargetId()
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public boolean deleteLikeRecord(Connection conn, LikeRecord record) throws SQLException {
		String sql = "DELETE FROM " + LIKE_TABLE + " WHERE `user_id`=? AND `target_id`=?;";
		Object[] params = {
				record.getUserid(), record.getTargetId()
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public boolean countUserLikeRecord(Connection conn, LikeRecord record) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + LIKE_TABLE + " WHERE `user_id`=? AND `target_id`=?;";
		Long l = qr.query(conn, sql, new ScalarHandler<Long>(), record.getUserid(), record.getTargetId());
		//存在记录返回true
		return l.equals(1L);
	}

	@Override
	public Long countLikeRecord(Connection conn, Long targetId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + LIKE_TABLE + " WHERE `target_id`=?;";
		return qr.query(conn, sql, new ScalarHandler<Long>(), targetId);
	}

	@Override
	public boolean checkUserLikeRecord(Connection conn, Long userid, Long targetId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + LIKE_TABLE + " WHERE `user_id`=? AND `target_id`=?;";
		Long query = qr.query(conn, sql, new ScalarHandler<Long>(), userid, targetId);
		//存在则已点赞
		return query.equals(1L);
	}


}
