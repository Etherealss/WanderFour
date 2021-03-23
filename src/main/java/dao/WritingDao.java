package dao;

import org.apache.ibatis.annotations.Param;
import pojo.bo.EsBo;
import pojo.po.Writing;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/5
 */
@SuppressWarnings("MybatisXMapperMethodInspection")
public interface WritingDao<T extends Writing> {

	/**
	 * 发布新作品
	 * @param t
	 * @return
	 * @throws SQLException
	 */
	Long createWritingInfo(T t);

	/***
	 * 储存新作品的内容
	 * @param id
	 * @param content
	 * @return
	 */
	void createWritingContent(@Param("id") Long id, @Param("content") String content);

	/**
	 * 更新文章
	 * @param t
	 * @return
	 */
	void updateWritingInfo(T t);

	/**
	 * 更新作品内容
	 * @param id
	 * @param content
	 * @return
	 */
	void updateWritingContent(@Param("id") Long id, @Param("content") String content);

	/**
	 * 获取文章的具体数据
	 * @param id
	 * @return
	 */
	T getWritingById(Long id);

	/**
	 * 获取作品内容
	 * @param id
	 * @return
	 */
	String getWritingContent(Long id);

	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	void deleteWritingById(Long id);

	/**
	 * 查询一共有多少文章
	 * @param partition
	 * @return
	 */
	Long countWriting(int partition);

	/**
	 * 按时间查询每页的文章记录
	 * @param partition
	 * @param order
	 * @param start     文章记录的起始索引
	 * @param rows      每一页显示的记录行数，也就是每一次查询要获取的记录数
	 * @return 包含了文章数据的List
	 */
	List<T> getWritingListByOrder(@Param("partition") int partition, @Param("order") String order,
	                              @Param("start") Long start, @Param("rows") int rows);

	/**
	 * 按时间查询每页的文章记录
	 * @param partition
	 * @param order
	 * @param start     文章记录的起始索引
	 * @param rows      每一页显示的记录行数，也就是每一次查询要获取的记录数
	 * @return 包含了文章数据的List
	 */
	List<T> getSimpleWritingListByOrder(@Param("partition") int partition, @Param("order") String order,
	                                    @Param("start") Long start, @Param("rows") int rows);


	/**
	 * 获取指定用户的博客总数
	 * @param userid 用户id
	 * @return 用户发表的文章数
	 * @throws SQLException
	 */
	long getUserWritingCount(long userid);

	/**
	 * 根据编号获取作者
	 * @param id
	 * @return
	 */
	long getAuthorByWritingId(Long id);

	/**
	 * 获取作品当前的点赞数
	 * @param id
	 * @return
	 */
	int getLikeCount(Long id);

	/**
	 * 更新作品的点赞数
	 * @param id
	 * @param count 新点赞数
	 */
	void updateLikeCount(@Param("id") Long id, @Param("count") Integer count);

	/**
	 * 获取所有文章/问贴的id
	 * @return
	 */
	List<Long> getAllWritingsId();

	/**
	 * 通过ids获取作品列表
	 * @param ids
	 * @return
	 */
	List<EsBo> getWritingsByIds(@Param("ids") List<Long> ids);
}
