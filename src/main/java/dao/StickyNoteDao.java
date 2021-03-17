package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.po.StickyNote;

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
	 * @param stickyNote
	 */
	void createNote(StickyNote stickyNote);

	/**
	 * 获取便利贴记录
	 * @param noteId
	 * @return
	 */
	StickyNote getStickyNote(Long noteId);

	/**
	 * 删除便利贴记录
	 * @param noteId
	 */
	void deleteStickyNote(Long noteId);

	/**
	 * 获取便利贴列表
	 * @param start
	 * @param rows
	 * @return
	 */
	List<StickyNote> getStickyNoteList(@Param("start") Long start, @Param("rows") int rows);
}
