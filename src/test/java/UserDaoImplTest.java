import pojo.po.User;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.UserDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class UserDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private UserDao ud = null;
	private Connection conn;

	@Before
	public void init() {
		ud = DaoFactory.getUserDAO();
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
	public void selectUserByPw() throws Exception {
		String email = "123123L";
		String pw = "123123";

		boolean b = ud.selectUserByPw(conn, email, pw);
		logger.info("selectUserByPw()测试结果：" + (b ? "存在" : "不存在"));
	}

	@Test
	public void selectUserById() throws Exception{
		String email = "123123L";
		boolean b = ud.selectUserByEmail(conn, email);
		logger.info("selectUserByPw()测试结果：" + (b ? "存在" : "不存在"));
	}
	@Test
	public void updateNewUser() throws SQLException {
		User user = new User("12333L", "1233", "李四", true, "null", "教师", new Date());
		ud.updateNewUser(conn, user);
	}
}