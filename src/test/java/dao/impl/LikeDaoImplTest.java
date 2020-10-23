package dao.impl;

import common.enums.TargetType;
import common.util.JdbcUtil;
import dao.LikeDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.LikeRecord;

import java.sql.Connection;
import java.sql.SQLException;

public class LikeDaoImplTest {

	private LikeDao dao = new LikeDaoImpl(TargetType.ARTICLE);
	private final Logger logger = Logger.getLogger(LikeDaoImplTest.class);
	private Connection conn;

	@Before
	public void init() throws Exception{
		conn = JdbcUtil.beginTransaction();
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
		record.setUserid(2L);
		boolean b = dao.createLikeRecord(conn, record);
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
		record.setUserid(2L);
		boolean b = dao.deleteLikeRecord(conn, record);
		logger.debug(b);
	}

	@Test
	public void countLikeRecord() throws SQLException {
		Long l = dao.countLikeRecord(conn, 2L);
		logger.debug(l);
	}
}