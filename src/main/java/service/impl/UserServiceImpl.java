package service.impl;

import common.bean.User;
import common.util.JdbcUtil;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserServiceImpl implements UserService {
	private final UserDao ud = new UserDaoImpl();

	@Override
	public boolean checkUserExist(Long userid)  {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			return ud.selectUserById(conn, userid);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(conn);
		}
		return false;
	}

	@Override
	public int validateUserLogin(Long userid, String paasword) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			if (!checkUserExist(userid)) {
				//账号不存在
				return 404;
			}
			if (ud.selectUserByPw(conn, userid, paasword)) {
				//密码正确，登录成功
				//TODO 检查异地登录
				return 200;
			} else {
				//密码错误
				return 400;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConnection(conn);
		}
		//出现异常
		return 500;
	}

	@Override
	public boolean registerNewUser(User user) {
		Connection conn = null;
		boolean b;
		try {
			conn = JdbcUtil.getConnection();
			b = ud.updateNewUser(conn, user);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		} finally {
			JdbcUtil.closeConnection(conn);
		}
		return b;
	}
}
