package dao;

import pojo.po.StickyNote;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/20
 */
public interface StickyNoteDao {

	/**
	 * 添加便利贴记录
	 * @param conn
	 * @param stickyNote
	 * @return
	 * @throws SQLException
	 */
	boolean createNote(Connection conn, StickyNote stickyNote) throws SQLException;

	/**
	 * 获取便利贴记录
	 * @param conn
	 * @param noteId
	 * @return
	 * @throws SQLException
	 */
	StickyNote getStickyNote(Connection conn, Long noteId) throws SQLException ;

	/**
	 * 删除便利贴记录
	 * @param conn
	 * @param noteId
	 * @return
	 * @throws SQLException
	 */
	boolean deleteStickyNote(Connection conn, Long noteId) throws SQLException ;

	/**
	 * 获取便利贴列表
	 * @param conn
	 * @param start
	 * @param rows
	 * @return
	 */
	List<StickyNote> getStickyNoteList(Connection conn, Long start, int rows) throws SQLException;
}
