package dao.impl;

import dao.WritingDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pojo.po.Article;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章DAO
 * @date 2020/10/5
 */
public class ArticleDaoImpl extends BaseDaoImpl implements WritingDao<Article> {

	@Override
	public boolean createWritingInfo(Connection conn, Article a) throws SQLException {
		String sql = "insert into `article` (`partition`, `category`, `author_id`, `title`," +
				"`label1`, `label2`, `label3`, `label4`, `label5`, `liked`, `collected`)" +
				"values(?,?,?,?, ?,?,?,?,?, ?,?)";
		Object[] params = {a.getPartition().code(), a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getLiked(), a.getCollected()
		};
		return qr.update(conn, sql, params) == 1;
	}

	@Override
	public BigInteger getLastInsertId(Connection conn) throws SQLException {
		return super.selectLastInsertId(conn);
	}

	@Override
	public boolean createWritingContent(Connection conn, Long id, String content) throws SQLException {
		String sql = "INSERT INTO `article_content` (`article_id`, `content`) VALUES(?, ?);";
		return qr.update(conn, sql, id, content) == 1;
	}

	@Override
	public boolean updateWritingInfo(Connection conn, Article a) throws SQLException {
		String sql = "UPDATE `article` SET " +
				"`partition`=?, `category`=?, `author_id`=?, `title`=?," +
				"`label1`=?, `label2`=?, `label3`=?, `label4`=?, `label5`=? " +
				"WHERE id=?";
		Object[] params = {a.getPartition().code(),
				a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getId()
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
	public Article getWritingById(Connection conn, Long id) throws SQLException {
		// 在储存partitionStr时，会调用对应的set方法，但是数据是复制到枚举属性partition上的
		String sql = "SELECT `article`.`id`, `name` `partitionStr`, `category`, `author_id` `authorId`," +
				" `title`, `label1`, `label2`, `label3`, `label4`,`label5`, " +
				"  `create_time` `createTime`, `update_time` `updateTime`, `liked`, `collected`" +
				" FROM `article` LEFT JOIN `partition` ON `article`.`partition` = `partition`.`id` WHERE `article`.`id`= ?;";
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
	public Long getAuthorByWritingId(Connection conn, Long articleId) throws SQLException {
		String sql = "SELECT `author_id` FROM `article` WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<Long>(), articleId);
	}

	@Override
	public Integer getLikeCount(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `liked` FROM `article` WHERE `id`=?";
		return qr.query(conn, sql, new ScalarHandler<Integer>(), id);
	}

	@Override
	public void updateLikeCount(Connection conn, Long id, Integer count) throws SQLException {
		String sql = "UPDATE `article` SET `liked`=? WHERE `id`=?;";
		int res = qr.update(conn, sql, count, id);
		assert res == 1;
	}
}
