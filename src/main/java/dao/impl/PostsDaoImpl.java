package dao.impl;

import dao.WritingDao;
import pojo.po.Posts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class PostsDaoImpl extends BaseDaoImpl<Posts> implements WritingDao<Posts> {

	@Override
	public void updateNewArticle(Connection conn, Posts article) throws SQLException {

	}

	@Override
	public void updateArticle(Connection conn, Posts posts) throws SQLException {

	}

	@Override
	public Posts selectArticleById(Connection conn, Long id) throws SQLException {
		return null;
	}

	@Override
	public void deleteArticleById(Connection conn, Long id) throws SQLException {

	}

	@Override
	public Long countArticle(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<Posts> getWritingListByPage(Connection conn, int partition, int start, int rows) throws SQLException {
		return null;
	}

	@Override
	public int getUserArticleCount(Connection conn, String userid) throws SQLException {
		return 0;
	}
}
