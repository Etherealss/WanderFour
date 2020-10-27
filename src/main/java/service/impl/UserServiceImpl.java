package service.impl;

import pojo.po.User;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.UserDao;
import service.UserService;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserServiceImpl implements UserService {
	private final UserDao dao = DaoFactory.getUserDAO();

	@Override
	public ResultType checkUserExist(String email) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		//检查账号是否已存在
		if (dao.countUserByEmail(conn, email)) {
			//存在
			return ResultType.IS_REGISTED;
		} else {
			//不存在
			return ResultType.USER_UN_FOUND;
		}
	}


	@Override
	public Long validateUserLogin(String email, String paasword) throws Exception {
		Connection conn;
		conn = JdbcUtil.getConnection();
		Long userid = dao.countUserBySign(conn, email, paasword);
		return userid;
	}

	@Override
	public Long registerNewUser(User user) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		boolean b1 = dao.updateNewUser(conn, user);
		Long lastInsertId = dao.getLastInsertId(conn).longValue();
		return lastInsertId;
	}
}
