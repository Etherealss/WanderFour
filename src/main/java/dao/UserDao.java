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
	BigInteger getLastInsertId(Connection conn) throws SQLException;

	/**
	 * 通过账号密码查询用户
	 * @param conn 数据库连接
	 * @param email
	 * @param password
	 * @return 存在返回true
	 * @throws SQLException
	 */
	User selectUserBySign(Connection conn, String email, String password) throws SQLException;

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
	boolean registerNewUser(Connection conn, User user) throws SQLException;

	/**
	 * 修改用户信息
	 * @param conn
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	boolean updateUserInfo(Connection conn, User user) throws SQLException;

	/**
	 * 修改用户密码
	 * @param conn
	 * @param userid
	 * @param pw
	 * @return
	 * @throws SQLException
	 */
	boolean updateUserPw(Connection conn, Long userid, String pw) throws SQLException;
	/**
	 * 查询用户，获取数据
	 * @param conn
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	User getUserByEmail(Connection conn, String email) throws SQLException;

	/**
	 * 查询用户，获取数据
	 * @param conn
	 * @param userid
	 * @return
	 * @throws SQLException
	 */
	User getUserById(Connection conn, Long userid) throws SQLException;

	/**
	 * 获取用户邮箱
	 * @param conn
	 * @param userid
	 * @return
	 * @throws SQLException
	 */
	User getUserEmailAndPwById(Connection conn, Long userid) throws SQLException;

	/**
	 * 根据id获取评论用户的头像和昵称
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	User getImgAndNicknameById(Connection conn, Long id) throws SQLException;
	
}
