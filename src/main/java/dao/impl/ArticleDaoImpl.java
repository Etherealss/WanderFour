package dao.impl;

import dao.WritingDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
	public void updateNewArticle(Connection conn, Article a) throws SQLException {
		String sql = "insert into " + getTableName() +
				"(`partition`, `category`, `author_id`, `title`, `content`," +
				"`label1`, `label2`, `label3`, `label4`, `label5`," +
				"`create_time`, `update_time`, `liked`, `collected`)" +
				"values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		Object[] params = {
				a.getPartition().code(), a.getCategory(), a.getAuthorId(), a.getTitle(), a.getContent(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getCreateTime(), a.getUpdateTime(), a.getLiked(), a.getCollected()
		};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public void updateArticle(Connection conn, Article a) throws SQLException {
		String sql = "UPDATE " + getTableName() + " SET " +
				"`partition`=?, `category`=?, `author_id`=?, `title`=?, `content`=?," +
				"`label1`=?, `label2`=?, `label3`=?, `label4`=?, `label5`=?, " +
				"`create_time`=?, `update_time`=?, `liked`=?, `collected`=? " +
				"WHERE id=?";
		Object[] params = {
				a.getPartition().code(), a.getCategory(), a.getAuthorId(), a.getTitle(), a.getContent(),
				a.getLabel1(), a.getLabel2(), a.getLabel3(), a.getLabel4(), a.getLabel5(),
				a.getCreateTime(), a.getUpdateTime(), a.getLiked(), a.getCollected(), a.getId()
		};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public Article selectArticleById(Connection conn, Long id) throws SQLException {
		String sql = "SELECT `article`.`id`, `name` `partitionStr`, `category`, `author_id` `authorId`," +
				" `title`, `content`, `label1`, `label2`, `label3`, `label4`,`label5`, " +
				"  `create_time` `createTime`, `update_time` `updateTime`, `liked`, `collected`" +
				" FROM `article`,`partition` WHERE `article`.`id`= ? AND `partition` = `partition`.`id`;";
		return qr.query(conn, sql, new BeanHandler<>(Article.class), id);
	}

	@Override
	public void deleteArticleById(Connection conn, Long id) throws SQLException {
		String sql = "";

	}

	@Override
	public Long countArticle(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<Article> getWritingListByPage(Connection conn, int partition, int start, int rows) throws SQLException {
		return null;
	}

	@Override
	public int getUserArticleCount(Connection conn, String userid) throws SQLException {
		return 0;
	}
}
