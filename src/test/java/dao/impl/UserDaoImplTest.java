package dao.impl;

import dao.UserDao;
import pojo.po.User;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class UserDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private UserDao dao = null;
	private Connection conn;

	@Before
	public void init() {
		dao = DaoFactory.getUserDAO();
		try {
			// 初始化数据连接
			conn = JdbcUtil.getConnection();
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
	}

	@After
	public void closeConn() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void testSelectUserByPw() throws Exception {
		String email = "123123L";
		String pw = "123123";

		boolean b = dao.countUserByEmailPw(conn, email, pw);
		logger.debug("selectUserByPw()测试结果：" + (b ? "存在" : "不存在"));
	}

	@Test
	public void testSelectUserById() throws Exception{
		String email = "1L";
		boolean b = dao.countUserByEmail(conn, email);
		logger.debug("testSelectUserById()测试结果：" + (b ? "存在" : "不存在"));
	}
	@Test
	public void testUpdateNewUser() throws SQLException {
		User user = new User("12333L", "1233", "李四", true, "null", "教师", new Date());
		dao.updateNewUser(conn, user);
	}

	@Test
	public void testSelectUserByEmail() throws SQLException {
		String emial = "1@qq.com";
		User user = dao.selectUserByEmail(conn, emial);
		logger.debug(user.toString());
	}
}