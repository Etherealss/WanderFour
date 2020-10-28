package dao.impl;

import org.apache.commons.dbutils.handlers.BeanHandler;
import pojo.po.User;
import dao.UserDao;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author 寒洲
 * @description 用户DAO
 * @date 2020/10/2
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public BigInteger getLastInsertId(Connection conn) throws SQLException {
		return super.selectLastInsertId(conn);
	}

	@Override
	public Long countUserBySign(Connection conn, String email, String password) throws SQLException {
		String sql = "SELECT `id` FROM `user` WHERE `email` = ? AND user_password = ?";
		Long userid = qr.query(conn, sql, new ScalarHandler<Long>(), email, password);
		return userid;
	}

	@Override
	public boolean countUserByEmail(Connection conn, String email) throws SQLException {
		String sql = "SELECT count(*) FROM `user` WHERE `email` = ?";
		Long count = qr.query(conn, sql, new ScalarHandler<Long>(), email);
		assert count < 2;
		return count > 0;
	}

	@Override
	public boolean updateNewUser(Connection conn, User u) throws SQLException {
		String sql = "insert into `user` (email, user_password, nickname, sex, avatar," +
				"register_date, user_type)values(?,?,?,?, ?,?,?)";
		//userType传int
		Object[] params = {u.getEmail(), u.getPassword(), u.getNickname(), u.getSex(),
				u.getAvatarPath(), new Date(), u.getUserType().code()};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public User getUserByEmail(Connection conn, String email) throws SQLException {
		String sql = "SELECT `user`.id , `email`, `user_password` `password`, `nickname`," +
				" `birthday`, `type` `userType`, `liked_count` `liked`, `collected_count` `collected`," +
				" `register_date` `registerDate` FROM  `user`, `user_type` " +
				"WHERE `user`.user_type = `user_type`.id AND `user`.email = ? ";
		return qr.query(conn, sql, new BeanHandler<>(User.class), email);
	}

	@Override
	public User getImgAndNicknameById(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `nickname`, `avatar` `avatarPath` FROM `user` WHERE `id`=?";
		return qr.query(conn, sql, new BeanHandler<>(User.class), id);
	}
}
