package service;

import org.apache.log4j.Logger;
import pojo.po.StickyNote;

import java.sql.Connection;
import java.util.List;

/**
 * @author 寒洲
 * @description 便利贴
 * @date 2020/11/20
 */
public interface StickyNoteService {


	/**
	 * 创建便利贴
	 * @param stickyNote
	 * @return
	 * @throws Exception
	 */
	boolean createStickyNote(StickyNote stickyNote) throws Exception;

	/**
	 * 获取便利贴列表
	 * @param currentPage
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	List<StickyNote> getStickyNoteList(int currentPage, int rows) throws Exception;
}
