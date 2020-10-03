import common.util.JdbcUtil;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import org.apache.log4j.Logger;
import org.junit.Test;

public class UserDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	private final UserDao ud;
	{
		ud = new UserDaoImpl();
	}
	@Test
	public void selectUserByPw() throws Exception {
		Long userid = 123123L;
		String pw = "123123";

		boolean b = ud.selectUserByPw(JdbcUtil.getConnection(), userid, pw);
		logger.info("selectUserByPw()测试结果：" + b);
	}
}