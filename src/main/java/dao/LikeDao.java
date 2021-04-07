package dao;

import org.apache.ibatis.annotations.Param;
import pojo.po.LikeRecord;

import java.sql.SQLException;

/**
 * @author 寒洲
 * @description 点赞DAO
 * @date 2020/10/14
 */
public interface LikeDao {

	/**
	 * 添加点赞记录
	 *
	 * @param likeTableName
	 * @param record
	 */
	void createLikeRecord(@Param("likeTableName") String likeTableName,
	                      @Param("record") LikeRecord record);

//	/**
//	 * 更新点赞记录
//	 * @param conn
//	 * @param record
//	 * @return
//	 */
//	boolean updateLikeRecord(Connection conn, LikeRecord record);

	/**
	 * 删除点赞记录
	 * @param likeTableName
	 * @param record
	 */
	void deleteLikeRecord(@Param("likeTableName") String likeTableName,
	                      @Param("record") LikeRecord record);

	/**
	 * 查看用户的点赞状态
	 *
	 * @param likeTableName
	 * @param record
	 * @return
	 */
	int countUserLikeRecord(@Param("likeTableName") String likeTableName,
	                        @Param("record") LikeRecord record);

	/**
	 * 统计点赞数
	 * @param likeTableName
	 * @param targetId
	 * @return
	 */
	int countLikeRecord(@Param("likeTableName") String likeTableName,
	                    @Param("targetId") Long targetId);

	/**
	 * 检查用户的点赞状态
	 * @param likeTableName
	 * @param userid
	 * @param targetId
	 * @return
	 */
	int checkUserLikeRecord(@Param("likeTableName") String likeTableName,
	                        @Param("userid") Long userid, Long targetId);
}
