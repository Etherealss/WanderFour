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
	 * 获取合适的数据库Id
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	Long selectMaxWritingId(Connection conn) throws SQLException;

	/**
	 * 发布新作品
	 * @param conn
	 * @param t
	 * @throws SQLException
	 * @return
	 */
	boolean updateNewWritingInfo(Connection conn, T t) throws SQLException;

	/***
	 * 储存新作品的内容
	 * @param conn
	 * @param id
	 * @param content
	 * @return
	 * @throws SQLException
	 */
	boolean updateNewWritingContent(Connection conn, Long id, String content) throws SQLException;

	/**
	 * 更新文章
	 * @param conn
	 * @param t
	 * @return
	 * @throws SQLException
	 */
	boolean updateWritingInfo(Connection conn, T t) throws SQLException;

	/**
	 * 更新作品内容
	 * @param conn
	 * @param id
	 * @param content
	 * @return
	 * @throws SQLException
	 */
	boolean updateWritingContent(Connection conn, Long id, String content) throws SQLException;

	/**
	 * 获取文章的具体数据
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	T selectWritingById(Connection conn, Long id) throws SQLException;

	/**
	 * 获取作品内容
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	String selectWritingContent(Connection conn, Long id) throws SQLException;
	/**
	 * 删除文章
	 * @param conn
	 * @param id
	 * @throws SQLException
	 * @return
	 */
	boolean deleteWritingById(Connection conn, Long id) throws SQLException;

	/**
	 * 查询一共有多少文章
	 * @param conn
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	Long countWriting(Connection conn, int partition) throws SQLException;

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
	 * @throws SQLException
	 */
	Long getUserWritingCount(Connection conn, String userid) throws SQLException;

	/**
	 * 根据编号获取作者
	 * @param conn
	 * @param articleId
	 * @return
	 * @throws SQLException
	 */
	String getAuthorByWritingId(Connection conn, Long articleId) throws SQLException;
}
