package service.impl;

import pojo.po.User;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.UserDao;
import service.UserService;

import java.math.BigInteger;
import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserServiceImpl implements UserService {
	private final UserDao dao = DaoFactory.getUserDAO();
	@Override
	public ResultType checkUserExist(String email)  {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			//检查账号是否已存在
			if (dao.countUserByEmail(conn, email)){
				//存在
				return ResultType.IS_REGISTED;
			}else{
				//不存在
				return ResultType.USER_UN_FOUND;
			}
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
		//出现异常
		return ResultType.EXCEPTION;
	}

	/**
	 * 提供给当前service内部的验证方法
	 * 检查用户id是否存在
	 * @param conn
	 * @param email
	 * @return
	 */
	private boolean checkUserExist(Connection conn, String email)  {
		try {
			return dao.countUserByEmail(conn, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultType validateUserLogin(String email, String paasword) {
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			if (!checkUserExist(conn, email)) {
				//账号不存在
				return ResultType.USER_UN_FOUND;
			}
			if (dao.countUserByEmailPw(conn, email, paasword)) {
				//密码正确，登录成功
				return ResultType.SUCCESS;
			} else {
				//密码错误
				return ResultType.PW_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//出现异常
		return ResultType.EXCEPTION;
	}

	@Override
	public Long registerNewUser(User user) {
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			boolean b1 = dao.updateNewUser(conn, user);
			Long lastInsertId = dao.selectLastInsertId(conn).longValue();
			return lastInsertId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
