import common.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class JdbcUtilTest {

	Connection conn;
	@Test
	public void getConnection() throws SQLException {

		conn = JdbcUtil.getConnection();
		System.out.println(conn);
		JdbcUtil.closeConnection(conn);
	}

	@Test
	public void closeResource() {
	}

	@Test
	public void testCloseResource() {
	}

	@Test
	public void closeConnection() throws SQLException {
		conn = JdbcUtil.getConnection();
		JdbcUtil.closeConnection(conn);
		System.out.println(conn);
	}
}