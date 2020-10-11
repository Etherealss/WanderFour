package dao;

import pojo.po.Writing;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/5
 */
public interface WritingDao<T extends Writing>{

	/**
	 * 发布新文章
	 * @param conn
	 * @param t
	 * @throws SQLException
	 */
	void updateNewArticle(Connection conn, T t) throws SQLException;

	/**
	 * 更新文章
	 * @param conn
	 * @param t
	 * @throws SQLException
	 */
	void updateArticle(Connection conn, T t) throws SQLException;
	/**
	 * 获取文章的具体数据
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	T selectArticleById(Connection conn, Long id) throws SQLException;

	/**
	 * 删除文章
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	void deleteArticleById(Connection conn, Long id) throws SQLException;

	/**
	 * 查询一共有多少文章，用于分页
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	Long countArticle(Connection conn, int partition) throws SQLException;

	/**
	 * 按页码查询每页的文章记录
	 * @param conn 数据库连接
	 * @param partition
	 * @param start 文章记录的起始索引
	 * @param rows 每一页显示的记录行数，也就是每一次查询要获取的记录数
	 * @return 包含了文章数据的List
	 * @throws SQLException
	 */
	List<T> getWritingListByPage(Connection conn, int partition, int start, int rows) throws SQLException;

	/**
	 * 获取指定用户的博客总数
	 * @param conn 数据库
	 * @param userid 用户id
	 * @return 用户发表的文章数
	 */
	int getUserArticleCount(Connection conn, String userid) throws SQLException;
}
