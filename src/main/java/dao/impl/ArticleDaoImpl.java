package dao.impl;

import dao.ArticleDao;
import pojo.po.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章DAO
 * @date 2020/10/5
 */
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao {

	@Override
	public void updateNewArticle(Connection conn, Article a) throws SQLException {
		String sql = "insert into " + getTableName() +
				"(a_partition, a_category, a_author_id, a_title," +
				"a_label1, a_label2, a_label3, a_label4, a_label5, " +
				"a_create_time, a_update_time, a_liked, a_collected)" +
				"values(?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		Object[] params = {a.getId(), a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(),a.getLabel2(),a.getLabel3(),a.getLabel4(),a.getLabel5(),
				a.getCreateTime(),a.getUpdateTime(),a.getLiked(),a.getCollected()};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public Article selectArticleById(Connection conn, Long id) throws SQLException {
		return null;
	}

	@Override
	public Long countArticle(Connection conn) throws SQLException {
		return null;
	}

	@Override
	public List<Article> getBlogListByPage(Connection conn, int start, int rows) throws SQLException{
		return null;
	}

	@Override
	public int getUserArticleCount(Connection conn, String userid) throws SQLException{
		return 0;
	}
}
