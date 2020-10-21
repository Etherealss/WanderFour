package dao.impl;

import dao.CategoryDao;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description 分类DAO实现类
 * @date 2020/10/19
 */
public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {
	private Logger logger = Logger.getLogger(CategoryDaoImpl.class);

	@Override
	public List<Map<String, Object>> selectAllCategoryByPart(Connection conn, int partition) throws SQLException {
		String sql = "SELECT `id`, `name` FROM `category` WHERE `partition`= ?;";
		return qr.query(conn, sql, new MapListHandler(), partition);
	}
}
