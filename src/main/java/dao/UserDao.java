package dao;

import pojo.po.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public interface UserDao {

	/**
	 * 通过账号密码查询用户
	 * @param conn 数据库连接
	 * @param email
	 * @param password
	 * @return 存在返回true
	 * @throws Exception
	 */
	boolean selectUserByPw(Connection conn, String email, String password) throws SQLException;

	/**
	 * 查询用户id是否存在
	 * @param conn 数据库连接
	 * @param email
	 * @return 存在返回true
	 */
	boolean selectUserByEmail(Connection conn, String email) throws SQLException;

	/**
	 * 新建用户
	 * @param conn
	 * @param user
	 */
	void updateNewUser(Connection conn, User user) throws SQLException;
}
