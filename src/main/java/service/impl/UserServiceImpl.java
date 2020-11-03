package service.impl;

import common.util.FileUtil;
import common.util.Md5Utils;
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
	public User getUserEmailAndPw(Long id) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		return dao.getUserEmailAndPwById(conn, id);
	}


	@Override
	public User validateUserLogin(String email, String paasword) throws Exception {
		Connection conn;
		conn = JdbcUtil.getConnection();
		User user = dao.selectUserBySign(conn, email, paasword);
		setUserAvatarStream(user);
		return user;
	}

	@Override
	public Long registerNewUser(User user) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		boolean b1 = dao.registerNewUser(conn, user);
		Long lastInsertId = dao.getLastInsertId(conn).longValue();
		return lastInsertId;
	}

	@Override
	public boolean updateUserInfo(User user) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		boolean b = dao.updateUserInfo(conn, user);
		return b;
	}


	@Override
	public User getLoggedUserInfo(Long userid) throws Exception {
		Connection conn;
		conn = JdbcUtil.getConnection();
		User user = dao.getUserById(conn, userid);
		setUserAvatarStream(user);
		return user;
	}

	@Override
	public ResultType updateUserPw(Long userid, String orginal, String newPw)  throws Exception{
		Connection conn = JdbcUtil.getConnection();
		User user = dao.getUserEmailAndPwById(conn, userid);
		//Md5加密
		orginal = Md5Utils.md5Encode(user.getEmail() + orginal);
		if (orginal.equals(user.getPassword())){
			//密码相同，可以修改
			boolean b = dao.updateUserPw(conn, userid, newPw);
			if (b){
				return ResultType.SUCCESS;
			} else {
				//修改失败
				return ResultType.EXCEPTION;
			}
		} else {
			//原密码错误
			return ResultType.PW_ERROR;
		}
	}

	private void setUserAvatarStream(User user){
		if (user != null) {
			//图片数据转码
			byte[] imgStream = FileUtil.getFileStream(user.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);
			user.setAvatarPath(imgByBase64);
		}
	}
}
