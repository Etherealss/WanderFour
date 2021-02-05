package dao.impl;

import common.enums.AttrEnum;
import dao.UserDao;
import org.apache.commons.dbutils.QueryRunner;
import pojo.po.User;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private UserDao dao = null;
	private Connection conn;

	@Before
	public void init() throws Exception {
		dao = DaoFactory.getUserDAO();
		conn = JdbcUtil.beginTransactionForTest();
	}

	@After
	public void closeConn() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void testSelectUserByPw() throws Exception {
		String email = "123456@qq.com";
		String pw = "2i8jdhgfnouflho1iqsl47tc4l";
		User b = dao.selectUserBySign(conn, email, pw);
		logger.debug("selectUserByPw()测试结果：" + b);
	}

	@Test
	public void testSelectUserById() throws Exception {
		Long email = 1L;
		User b = dao.getUserById(conn, email);
		logger.debug(b);
	}

	@Test
	public void testUpdateNewUser() throws SQLException {
		User user = new User("12333L", "1233", "李四", true, "null", "教师", new Date());
		dao.registerNewUser(conn, user);
	}

	@Test
	public void testSelectUserByEmail() throws SQLException {
		String emial = "123456@qq.com";
		User user = dao.getUserByEmail(conn, emial);
		logger.debug(user);
	}

	@Test
	public void testPw() throws Exception {
		Long userId = 4L;
		// 2i8jdhgfnouflho1iqsl47tc4l
		String pw = "2i8jdhgfnouflho1iqsl47tc4l";
		boolean b = dao.updateUserPw(conn, userId, pw);
		logger.debug(b);
	}

	@Test
	public void testUpdateUserAvatarPath() throws Exception {
		Long userId = 4L;
		String filePath = AttrEnum.AVATAR_PATH + userId + ".png";
		logger.debug(filePath);
		boolean b = dao.updateUserAvatarPath(conn, userId, filePath);
		logger.debug(b);
	}

	@Test
	public void testGetUsers() throws Exception {
		List<Long> list = new ArrayList<>();
		list.add(1L);
		list.add(2L);
		List<User> usersInfo = dao.getUsersInfo(conn, list);
		for (User user : usersInfo) {
			logger.debug(user);
		}
	}
}