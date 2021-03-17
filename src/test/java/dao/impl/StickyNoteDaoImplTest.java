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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.StickyNote;

import java.sql.Connection;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class StickyNoteDaoImplTest {

	@Autowired
	private StickyNoteDao dao;
	private Logger logger = Logger.getLogger(StickyNoteDaoImplTest.class);

	@Test
	public void testCreateNote() throws Exception {
	}

	@Test
	public void testGetStickyNote() throws Exception {
		StickyNote stickyNote = dao.getStickyNote(1L);
		logger.debug(stickyNote);
	}

	@Test
	public void testDeleteStickyNote() throws Exception {
	}

	@Test
	public void testGetStickyNoteList() throws Exception {
	}
}