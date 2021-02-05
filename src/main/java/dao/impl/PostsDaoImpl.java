package dao.impl;

import dao.WritingDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pojo.bo.EsBo;
import pojo.po.Posts;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/20
 */
public class PostsDaoImpl extends BaseDaoImpl implements WritingDao<Posts> {

	@Override
	public boolean createWritingInfo(Connection conn, Posts p) throws SQLException {
		String sql = "insert into `posts` (`category`, `author_id`, `title`," +
				"`content`, `label1`, `label2`, `label3`, `label4`, `label5`, `liked`, `follow`)" +
				"values(?,?,?,?, ?,?,?,?,?, ?,?)";
		Object[] params = {
				p.getCategory(), p.getAuthorId(), p.getTitle(), p.getContent(),
				p.getLabel1(), p.getLabel2(), p.getLabel3(), p.getLabel4(), p.getLabel5(),
				p.getLiked(), p.getCollected()
		};
		return qr.update(conn, sql, params) == 1;
	}

	@Override
	public BigInteger getLastInsertId(Connection conn) throws SQLException {
		return super.selectLastInsertId(conn);

	}

	@Override
	public boolean createWritingContent(Connection conn, Long id, String content) throws SQLException {
		return false;
	}

	@Override
	public boolean updateWritingInfo(Connection conn, Posts p) throws SQLException {
		String sql = "UPDATE `posts` SET " +
				"`category`=?, `author_id`=?, `title`=?, `content`=?, " +
				"`label1`=?, `label2`=?, `label3`=?, `label4`=?, `label5`=? " +
				"WHERE id=?";
		Object[] params = {
				p.getCategory(), p.getAuthorId(), p.getTitle(), p.getContent(),
				p.getLabel1(), p.getLabel2(), p.getLabel3(), p.getLabel4(), p.getLabel5(),
				p.getId()
		};
		int res = qr.update(conn, sql, params);
		return res == 1;
	}

	@Override
	public boolean updateWritingContent(Connection conn, Long id, String content) throws SQLException {
		return false;
	}

	@Override
	public Posts getWritingById(Connection conn, Long id) throws SQLException {
		// 在储存partitionStr时，会调用对应的set方法，但是数据是复制到枚举属性partition上的
		String sql = "SELECT " +
				" `posts`.`id`,\n" +
				" `partition`.`name` `partitionStr`, " +
				" `posts`.`category`, " +
				" `posts`.`content`, " +
				" `author_id` `authorId`, " +
				" `title`, " +
				" `label1`,`label2`,`label3`,`label4`,`label5`, " +
				" `create_time` `createTime`, " +
				" `update_time` `updateTime`, " +
				" `liked`, " +
				" `follow` `collected` " +
				"FROM `posts` " +
				"LEFT JOIN `category` ON `posts`.`category` = `category`.`id` " +
				"LEFT JOIN `partition` ON `category`.`partition` = `partition`.`id` " +
				"WHERE `posts`.`id`= ?;";
		return qr.query(conn, sql, new BeanHandler<>(Posts.class), id);
	}

	@Override
	public String getWritingContent(Connection conn, Long id) throws SQLException {
		return null;
	}

	@Override
	public boolean deleteWritingById(Connection conn, Long id) throws SQLException {
		String sql = "DELETE FROM `posts` WHERE id=?";
		int res = qr.update(conn, sql, id);
		return res == 1;
	}

	@Override
	public Long countWriting(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<Posts> getWritingListByOrder(Connection conn, int partition, String order, Long start, int rows) throws SQLException {
		String sql = "SELECT `posts`.`id`,`partition`.`name` `partitionStr`, `category`.`id` category," +
				" `author_id` `authorId`,`title`, " +
				" `label1`,`label2`,`label3`,`label4`,`label5`, " +
				" `create_time`,`update_time`,`liked`,`follow` " +
				"FROM `posts` " +
				"LEFT JOIN `category` ON `posts`.`category` = `category`.`id` " +
				"LEFT JOIN `partition` ON `category`.`partition` = `partition`.`id` " +
				"WHERE `category`.`partition`=? " +
				"ORDER BY " + order + " DESC LIMIT ?,?;";
		return qr.query(conn, sql, new BeanListHandler<>(Posts.class), partition, start, rows);
	}

	@Override
	public List<Posts> getSimpleWritingListByOrder(Connection conn, int partition, String order, Long start, int rows) throws SQLException {
		String sql = "SELECT `posts`.`id`, `title` " +
				"FROM `posts` " +
				"LEFT JOIN `category` ON `posts`.`category` = `category`.`id` " +
				"LEFT JOIN `partition` ON `category`.`partition` = `partition`.`id` " +
				"WHERE `partition`.`id`=? " +
				"ORDER BY " + order + " DESC LIMIT ?,? ";
		return qr.query(conn, sql, new BeanListHandler<>(Posts.class), partition, start, rows);
	}

	@Override
	public Long getUserWritingCount(Connection conn, String userid) throws SQLException {
		return null;
	}

	@Override
	public Long getAuthorByWritingId(Connection conn, Long articleId) throws SQLException {
		String sql = "SELECT `author_id` FROM `posts` WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<Long>(), articleId);
	}

	@Override
	public Integer getLikeCount(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `liked` FROM `posts` WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<Integer>(), id);
	}

	@Override
	public void updateLikeCount(Connection conn, Long id, Integer count) throws SQLException {
		String sql = "UPDATE `posts` SET `liked`=? WHERE `id`=?;";
		int res = qr.update(conn, sql, count, id);
		assert res == 1;
	}

	@Override
	public List<Long> getAllWritingsId(Connection conn) throws SQLException {
		String sql = "SELECT `id` FROM `posts`";
		return qr.query(conn, sql, new ColumnListHandler<Long>("id"));
	}

	@Override
	public List<EsBo> getWritingsByIds(Connection conn, List<Long> ids) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT `posts`.`id` `writingId`,`category`.`id` `categoryId`, `category`.`name` `categoryName`,`content`, `author_id` `authorId`, " +
						"`title`, `label1`, `label2`, `label3`, `label4`,`label5`, " +
						" `create_time` `createTime`, `update_time` `updateTime`, `liked`, `follow` `collected` " +
						"FROM `posts` LEFT JOIN `category` ON `posts`.`category` = `category`.`id` WHERE `posts`.`id` IN (");
		for (Long id : ids) {
			sql.append(id.toString()).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(");");
		return qr.query(conn, sql.toString(), new BeanListHandler<>(EsBo.class));
	}
}
