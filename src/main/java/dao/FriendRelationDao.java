package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/21
 */
public interface FriendRelationDao {

	/**
	 * 获取好友列表Id
	 * @return
	 * @param conn
	 * @param userId
	 */
	List<Long> getFriendsId(Connection conn, Long userId) throws SQLException;
}
