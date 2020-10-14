package dao;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.impl.LikeDaoImpl;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.LikeRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class LikeDaoImplTest {

	private LikeDao dao = new LikeDaoImpl();
	private final Logger logger = Logger.getLogger(LikeDaoImplTest.class);
	private Connection conn;

	@Before
	public void init() {
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
	public void insertLikeRecord() throws SQLException {
		LikeRecord record = new LikeRecord();
		record.setLikeState(1);
		record.setTargetId(2L);
		record.setTargetType(1);
		record.setUserid("123456@qq.com");
		boolean b = dao.insertLikeRecord(conn, record);
		logger.debug(b);
	}

//	@Test
//	public void updateLikeRecord() throws SQLException {
//		LikeRecord record = new LikeRecord();
//		record.setLikeState(1);
//		record.setLikeTime(new Date());
//		record.setTargetId(2L);
//		record.setTargetType(1);
//		record.setUserid("123456@qq.com");
//		boolean b = dao.updateLikeRecord(conn, record);
//		logger.debug(b);
//	}

	@Test
	public void deleteLikeRecord() throws SQLException {
		LikeRecord record = new LikeRecord();
		record.setTargetId(2L);
		record.setTargetType(1);
		record.setUserid("123456@qq.com");
		boolean b = dao.deleteLikeRecord(conn, record);
		logger.debug(b);
	}

	@Test
	public void countLikeRecord() throws SQLException {
		Long l = dao.countLikeRecord(conn, 2L, 1);
		logger.debug(l);
	}
}