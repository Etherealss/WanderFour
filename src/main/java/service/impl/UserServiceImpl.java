package service.impl;

import pojo.po.User;
import common.dto.ResultState;
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
	private final UserDao ud = DaoFactory.getUserDAO();

	@Override
	public ResultState checkUserExist(String email)  {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			//检查账号是否已存在
			if (ud.countUserByEmail(conn, email)){
				//存在
				return ResultState.SUCCESS;
			}else{
				//不存在
				return ResultState.USER_UN_FOUND;
			}
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
		//出现异常
		return ResultState.EXCEPTION;
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
			return ud.countUserByEmail(conn, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultState validateUserLogin(String email, String paasword) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			if (!checkUserExist(conn, email)) {
				//账号不存在
				return ResultState.USER_UN_FOUND;
			}
			if (ud.countUserByPw(conn, email, paasword)) {
				//密码正确，登录成功
				return ResultState.SUCCESS;
			} else {
				//密码错误
				return ResultState.PW_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//出现异常
		return ResultState.EXCEPTION;
	}

	@Override
	public ResultState registerNewUser(User user) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			ud.updateNewUser(conn, user);
			return ResultState.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}
}
