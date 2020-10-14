package dao.impl;

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
public class LikeDaoImpl extends BaseDaoImpl<LikeRecord> implements LikeDao {
	private Logger logger = Logger.getLogger(LikeDaoImpl.class);

	@Override
	public boolean insertLikeRecord(Connection conn, LikeRecord record) throws SQLException {
		String sql = "INSERT INTO `user_like_record` (`user_email`, `target_id`, `target_type`) VALUES(?,?,?);";
		Object[] params = {
				record.getUserid(), record.getTargetId(), record.getTargetType(),
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

//	@Override
//	public boolean updateLikeRecord(Connection conn, LikeRecord record) throws SQLException {
//		String sql = "UPDATE `user_like_record` SET `user_email`=?, `target_id`=?, `target_type`=?, `update_time`=? WHERE `id`=?;";
//		Object[] params = {
//				record.getUserid(), record.getTargetId(), record.getTargetType(), record.getLikeTime(), record.getId()
//		};
//		int res = qr.update(conn, sql, params);
//		return res == 1;
//	}

	@Override
	public boolean deleteLikeRecord(Connection conn, LikeRecord record) throws SQLException {
		String sql = "DELETE FROM `user_like_record` WHERE `user_email`=? AND `target_id`=? AND `target_type`=?;";
		Object[] params = {
				record.getUserid(), record.getTargetId(), record.getTargetType()
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public Long countLikeRecord(Connection conn, Long targetId, int targetType) throws SQLException {
		String sql = "SELECT COUNT(*) FROM `user_like_record` WHERE `target_id`=? AND `target_type`=?;";
		return qr.query(conn, sql, new ScalarHandler<Long>(), targetId, targetType);
	}


}
