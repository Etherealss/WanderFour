package dao.impl;

import static org.junit.Assert.*;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import dao.StickyNoteDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class StickyNoteDaoImplTest {

	private Logger logger = Logger.getLogger(StickyNoteDaoImplTest.class);
	StickyNoteDao dao = DaoFactory.getStickyNoteDao();
	Connection conn;

	@Before
	public void doBefore() throws Exception {
		conn = JdbcUtil.beginTransactionForTest();
	}

	@After
	public void doAfter() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void testCreateNote() throws Exception {
	}

	@Test
	public void testGetStickyNote() throws Exception {
	}

	@Test
	public void testDeleteStickyNote() throws Exception {
	}

	@Test
	public void testGetStickyNoteList() throws Exception {
	}
}