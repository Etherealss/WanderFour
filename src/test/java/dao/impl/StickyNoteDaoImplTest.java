package dao.impl;

import dao.StickyNoteDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.StickyNote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class StickyNoteDaoImplTest {

	@Autowired
	private StickyNoteDao dao;
	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void testCreateNote() throws Exception {
	}

	@Test
	public void testGetStickyNote() throws Exception {
		StickyNote stickyNote = dao.getStickyNote(1L);
		logger.debug(stickyNote.toString());
	}

	@Test
	public void testDeleteStickyNote() throws Exception {
	}

	@Test
	public void testGetStickyNoteList() throws Exception {
	}
}