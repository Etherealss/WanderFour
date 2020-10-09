package dao.impl;

import org.apache.commons.dbutils.handlers.BeanHandler;
import pojo.po.User;
import dao.UserDao;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public boolean countUserByPw(Connection conn, String email, String password) throws SQLException {
		String sql = "SELECT count(*) FROM " + getTableName() + " WHERE `email` = ? AND u_password = ?";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), email, password);
		//email唯一，结果至多为1
		assert count < 2;
		return count > 0;
	}

	@Override
	public boolean countUserByEmail(Connection conn, String email) throws SQLException {
		String sql = "SELECT count(*) FROM " + getTableName() + " WHERE `email` = ?";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), email);
		assert count < 2;
		return count > 0;
	}

	@Override
	public void updateNewUser(Connection conn, User u) throws SQLException {
		String sql = "insert into " + getTableName() + "(email, u_password, nickname, sex, avatar," +
				"register_date, u_type)values(?,?,?,?, ?,?,?)";
		Object[] params = {u.getUserid(), u.getPassword(), u.getNickname(), u.getSex(),
				u.getAvatarPath(), new Date(), u.getUserType()};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public User selectUserByEmail(Connection conn, String email) throws SQLException {
		String sql = "SELECT `email` `userid`, `u_password` `password`, `nickname`, `birthday`, `type` `userType`, u_liked liked, u_collected collected, register_date registerDate " +
				"FROM  `user`, `user_type` " +
				"WHERE `user`.u_type = `user_type`.id";
		return qr.query(conn, sql, new BeanHandler<>(User.class));
	}
}
