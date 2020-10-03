package dao.impl;

import common.bean.User;
import dao.UserDao;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public boolean selectUserByPw(Connection conn, Long userid, String password) throws Exception{
		String sql = "SELECT count(*) FROM `wanderfour`.`user` WHERE u_id = ? AND u_password = ?";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), userid, password);
		return count > 0;
	}

	@Override
	public boolean selectUserById(Connection conn, Long userid) {
		return false;
	}

	@Override
	public boolean updateNewUser(Connection conn, User user) {
		return false;
	}
}
