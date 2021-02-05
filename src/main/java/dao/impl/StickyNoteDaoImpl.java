package dao.impl;

import dao.StickyNoteDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pojo.po.Article;
import pojo.po.StickyNote;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/20
 */
public class StickyNoteDaoImpl extends BaseDaoImpl implements StickyNoteDao {

	@Override
	public boolean createNote(Connection conn, StickyNote stickyNote) throws SQLException {
		String sql = "INSERT INTO `sticky_note` " +
				"(`id`,`author_id`,`content`,`create_time`,`like_count`," +
				"`shape`,`alpha`,`red`,`green`,`blue`,`angle`)" +
				"VALUES (?,?,?,?,?, ?,?,?,?,?, ?);";
		Object[] params = {stickyNote.getId(), stickyNote.getAuthorId(),
				stickyNote.getContent(), stickyNote.getCreateTime(),
				stickyNote.getLikeCount(), stickyNote.getShape(),
				stickyNote.getAlpha(), stickyNote.getRed(), stickyNote.getGreen(),
				stickyNote.getBlue(), stickyNote.getAngle()
		};
		return qr.update(conn, sql, params) == 1;
	}

	@Override
	public StickyNote getStickyNote(Connection conn, Long noteId) throws SQLException {
		String sql = "SELECT `id`,`author_id` `authorId`,`content`,`create_time` `createTime`, \n" +
				"`like_count` `likeCount`,`shape`,`alpha`,`red`,`green`,`blue`,`angle` \n" +
				"FROM `sticky_note` WHERE `id`=? ";
		return qr.query(conn, sql, new BeanHandler<>(StickyNote.class), noteId);
	}

	@Override
	public boolean deleteStickyNote(Connection conn, Long noteId) throws SQLException {
		String sql = "DELETE FROM `sticky_note` WHERE id=?;";
		return qr.update(conn, sql, noteId) == 1;
	}

	@Override
	public List<StickyNote> getStickyNoteList(Connection conn, Long start, int rows) throws SQLException {
		String sql = "SELECT `id`,`author_id` `authorId`,`content`,`create_time` `createTime`, \n" +
				"`like_count` `likeCount`,`shape`,`alpha`,`red`,`green`,`blue`,`angle` \n" +
				"FROM `sticky_note` LIMIT ?,?;";
		return qr.query(conn, sql, new BeanListHandler<>(StickyNote.class), start, rows);
	}
}
