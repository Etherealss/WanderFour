package dao;

import pojo.po.LikeRecord;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description 点赞DAO
 * @date 2020/10/14
 */
public interface LikeDao {

	/**
	 * 添加点赞记录
	 * @param conn
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	boolean insertLikeRecord(Connection conn, LikeRecord record) throws SQLException;

//	/**
//	 * 更新点赞记录
//	 * @param conn
//	 * @param record
//	 * @return
//	 * @throws SQLException
//	 */
//	boolean updateLikeRecord(Connection conn, LikeRecord record) throws SQLException;

	/**
	 * 删除点赞记录
	 * @param conn
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	boolean deleteLikeRecord(Connection conn, LikeRecord record) throws SQLException;

	/**
	 * 统计点赞数
	 * @param conn
	 * @param targetId
	 * @param targetType
	 * @return
	 * @throws SQLException
	 */
	Long countLikeRecord(Connection conn, Long targetId, int targetType) throws SQLException;
}
