package dao;

import pojo.po.User;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description 用户DAO
 * @date 2020/10/2
 */
public interface UserDao {

	/**
	 * 获取合适的数据库Id
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	BigInteger selectLastInsertId(Connection conn) throws SQLException;

	/**
	 * 通过账号密码查询用户
	 * @param conn 数据库连接
	 * @param email
	 * @param password
	 * @return 存在返回true
	 * @throws SQLException
	 */
	boolean countUserByEmailPw(Connection conn, String email, String password) throws SQLException;

	/**
	 * 查询用户id是否存在
	 * @param conn 数据库连接
	 * @param email
	 * @return 存在返回true
	 * @throws SQLException
	 */
	boolean countUserByEmail(Connection conn, String email) throws SQLException;

	/**
	 * 新建用户
	 * @param conn
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	boolean updateNewUser(Connection conn, User user) throws SQLException;

	/**
	 * 查询用户，获取数据
	 * @param conn
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	User selectUserByEmail(Connection conn, String email) throws SQLException;
	
}
