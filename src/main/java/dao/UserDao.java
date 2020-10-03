package dao;

import common.bean.User;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public interface UserDao {

	/**
	 * 通过账号密码查询用户
	 * @param conn 数据库连接
	 * @param userid
	 * @param password
	 * @return 存在返回true
	 * @throws Exception
	 */
	boolean selectUserByPw(Connection conn, Long userid, String password) throws Exception;

	/**
	 * 查询用户id是否存在
	 * @param conn 数据库连接
	 * @param userid
	 * @return 存在返回true
	 */
	boolean selectUserById(Connection conn, Long userid);

	/**
	 * 新建用户
	 * @param conn
	 * @param user
	 * @return
	 */
	boolean updateNewUser(Connection conn, User user);
}
