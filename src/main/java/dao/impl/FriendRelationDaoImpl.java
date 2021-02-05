package dao.impl;

import dao.FriendRelationDao;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/21
 */
public class FriendRelationDaoImpl extends BaseDaoImpl implements FriendRelationDao {
	@Override
	public List<Long> getFriendsId(Connection conn, Long userId) throws SQLException {
		String sql = "SELECT `friend_id` FROM `friend_relation` WHERE `user_id`=?";
		return qr.query(conn, sql, new ColumnListHandler<>("friend_id"), userId);
	}
}
