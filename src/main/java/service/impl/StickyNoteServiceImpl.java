package service.impl;

import dao.StickyNoteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.po.StickyNote;
import service.StickyNoteService;

import java.util.List;

/**
 * @author 寒洲
 * @description 便利贴
 * @date 2020/11/20
 */
public class StickyNoteServiceImpl implements StickyNoteService {

	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	@Autowired
	private StickyNoteDao dao;

	@Override
	public void createStickyNote(StickyNote stickyNote) {
		logger.trace("创建便利贴");
		dao.createNote(stickyNote);
	}

	@Override
	public List<StickyNote> getStickyNoteList(int currentPage, int rows) {
		logger.trace("获取便利贴列表");
		// 计算起始记录数
		long start = (currentPage - 1L) * rows;
		return dao.getStickyNoteList(start, rows);
	}
}
