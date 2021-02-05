package service.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.StickyNoteDao;
import org.apache.log4j.Logger;
import pojo.po.StickyNote;
import service.StickyNoteService;

import java.sql.Connection;
import java.util.List;

/**
 * @author 寒洲
 * @description 便利贴
 * @date 2020/11/20
 */
public class StickyNoteServiceImpl implements StickyNoteService {

	private Logger logger = Logger.getLogger(StickyNoteService.class);

	private StickyNoteDao dao = DaoFactory.getStickyNoteDao();

	@Override
	public boolean createStickyNote(StickyNote stickyNote) throws Exception {
		logger.trace("创建便利贴");
		Connection conn = JdbcUtil.getConnection();
		return dao.createNote(conn, stickyNote);
	}

	@Override
	public List<StickyNote> getStickyNoteList(int currentPage, int rows) throws Exception {
		logger.trace("获取便利贴列表");
		Connection conn = JdbcUtil.getConnection();
		// 计算起始记录数
		long start = (currentPage - 1L) * rows;
		return dao.getStickyNoteList(conn, start, rows);
	}
}
