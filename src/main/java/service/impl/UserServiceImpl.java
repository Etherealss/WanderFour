package service.impl;

import common.bean.User;
import common.util.JdbcUtil;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserServiceImpl implements UserService {
	private UserDao ud = new UserDaoImpl();

	@Override
	public boolean checkUserExist(Long userid) {
		return false;
	}

	@Override
	public boolean validateUserLogin(Long userid, String paasword) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			return ud.selectUserByPw(conn, userid, paasword);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(conn);
		}
		return false;
	}

	@Override
	public boolean registerNewUser(User user) {
		return false;
	}
}
