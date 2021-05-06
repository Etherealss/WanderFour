package dao.impl;

import dao.CategoryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class CategoryDaoImplTest {
	private Logger logger = LoggerFactory.getLogger("testLogger");
	@Autowired
	private CategoryDao dao = null;

	@Test
	public void selectAllCategoryName() throws SQLException {
		logger.debug("获取分类：");
		List<Map<String, Object>> maps = dao.getAllCategoryByPart(1);
		for(Map<String, Object> map : maps){
			System.out.println(map.get("id") + ":" + map.get("name"));
		}
		logger.debug("获取分类完毕！！");
	}
}