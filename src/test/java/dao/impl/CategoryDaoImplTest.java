package dao.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.CategoryDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CategoryDaoImplTest {

	private final Logger logger = Logger.getLogger(CategoryDaoImplTest.class);
	private CategoryDao dao = null;
	private Connection conn;

	@Before
	public void setUp() throws Exception {
		dao = DaoFactory.getCategoryDao();
		conn = JdbcUtil.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		JdbcUtil.closeTransaction();
	}

	@Test
	public void selectAllCategoryName() throws SQLException {
		logger.debug("获取分类：");
		List<Map<String, Object>> maps = dao.getAllCategoryByPart(conn, 1);
		for(Map<String, Object> map : maps){
			System.out.println(map.get("id") + ":" + map.get("name"));
		}
		logger.debug("获取分类完毕！！");
	}
}