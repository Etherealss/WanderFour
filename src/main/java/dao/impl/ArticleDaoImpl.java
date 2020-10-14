package dao.impl;

import dao.WritingDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pojo.po.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章DAO
 * @date 2020/10/5
 */
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements WritingDao<Article> {

	@Override
	public Long selectMaxWritingId(Connection conn) throws SQLException {
		String sql = "SELECT MAX(`id`) FROM `article`";
		return qr.query(conn, sql, new ScalarHandler<Long>());
	}

	@Override
	public boolean updateNewWritingInfo(Connection conn, Article a) throws SQLException {
		String sql = "insert into " + getTableName() +
				"(`id`, `partition`, `category`, `author_id`, `title`," +
				"`label1`, `label2`, `label3`, `label4`, `label5`," +
				"`create_time`, `update_time`, `liked`, `collected`)" +
				"values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		Object[] params = {a.getId(),
				a.getPartition().code(), a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getCreateTime(), a.getUpdateTime(), a.getLiked(), a.getCollected()
		};
		return qr.update(conn, sql, params) == 1;
	}

	@Override
	public boolean updateNewWritingContent(Connection conn, Long id, String content) throws SQLException {
		String sql = "INSERT INTO `article_content` (`article_id`, `content`) VALUES(?, ?);";
		return qr.update(conn, sql, id, content) == 1;
	}

	@Override
	public boolean updateWritingInfo(Connection conn, Article a) throws SQLException {
		String sql = "UPDATE " + getTableName() + " SET " +
				"`partition`=?, `category`=?, `author_id`=?, `title`=?," +
				"`label1`=?, `label2`=?, `label3`=?, `label4`=?, `label5`=?, " +
				"`create_time`=?, `update_time`=?, `liked`=?, `collected`=? " +
				"WHERE id=?";
		Object[] params = {
				a.getPartition().code(), a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getCreateTime(), a.getUpdateTime(), a.getLiked(), a.getCollected(), a.getId()
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public boolean updateWritingContent(Connection conn, Long id, String content) throws SQLException {
		String sql = "UPDATE `article_content` SET `content`=? WHERE `article_id`=?;";
		int res = qr.update(conn, sql, content, id);
		return res == 1;
	}

	@Override
	public Article selectWritingById(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `article`.`id`, `name` `partitionStr`, `category`, `author_id` `authorId`," +
				" `title`, `label1`, `label2`, `label3`, `label4`,`label5`, " +
				"  `create_time` `createTime`, `update_time` `updateTime`, `liked`, `collected`" +
				" FROM `article`,`partition` WHERE `article`.`id`= ? AND `partition` = `partition`.`id`;";
		return qr.query(conn, sql, new BeanHandler<>(Article.class), id);
	}

	@Override
	public String selectWritingContent(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `content` FROM `article_content` WHERE `article_id`=?";
		return qr.query(conn, sql, new ScalarHandler<String>(), id);
	}

	@Override
	public boolean deleteWritingById(Connection conn, Long id) throws SQLException {
		String sql = "DELETE FROM `article` WHERE id=?";
		int res = qr.update(conn, sql, id);
		return res == 1;
	}

	@Override
	public Long countWriting(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<Article> getWritingListByPage(Connection conn, int partition, int start, int rows) throws SQLException {
		return null;
	}

	@Override
	public Long getUserWritingCount(Connection conn, String userid) throws SQLException {
		return 0L;
	}

	@Override
	public String getAuthorByWritingId(Connection conn, Long articleId) throws SQLException {
		String sql = "SELECT `author_id` FROM `article` WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<String>(), articleId);
	}
}
