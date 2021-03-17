package dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description 分类表的DAO
 * @date 2020/10/19
 */
public interface CategoryDao {

	/**
	 * 获取数据库分类表中指定分区的分类id和分类name
	 * @param partition
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> getAllCategoryByPart(int partition) throws SQLException;
}
