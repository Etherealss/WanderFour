import common.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtilTest {

	Connection conn;
	@Test
	public void getConnection() throws Exception {

		conn = JdbcUtil.getConnection();
		System.out.println(conn);
		JdbcUtil.closeTransaction();
	}

	@Test
	public void closeResource() {
	}

	@Test
	public void testCloseResource() {
	}

	@Test
	public void closeConnection() throws Exception {
		conn = JdbcUtil.getConnection();
		JdbcUtil.closeTransaction();
		System.out.println(conn);
	}
}