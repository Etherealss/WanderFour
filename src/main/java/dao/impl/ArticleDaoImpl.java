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
public class ArticleDaoImpl<T extends Article> extends BaseDaoImpl<Article> implements ArticleDao<T> {

	@Override
	public void updateNewArticle(Connection conn, Article a) throws SQLException {
		String sql = "insert into " + getTableName() +
				"(category, author_id, title," +
				"label1, label2, label3, label4, label5, " +
				"create_time, update_time, liked, collected)" +
				"values(?,?,?, ?,?,?,?,?, ?,?,?,?)";
		Object[] params = {a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(),a.getLabel2(),a.getLabel3(),a.getLabel4(),a.getLabel5(),
				a.getCreateTime(),a.getUpdateTime(),a.getLiked(),a.getCollected()};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public T selectArticleById(Connection conn, Long id) throws SQLException {
		return null;
	}

	@Override
	public Long countArticle(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<T> getBlogListByPage(Connection conn, int partition, int start, int rows) throws SQLException{
		return null;
	}

	@Override
	public int getUserArticleCount(Connection conn, String userid) throws SQLException{
		return 0;
	}
}
