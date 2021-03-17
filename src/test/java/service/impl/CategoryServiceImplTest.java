package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.CategoryService;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class CategoryServiceImplTest {

	@Autowired
	private CategoryService service;

	Logger logger = Logger.getLogger(CategoryServiceImplTest.class);
	@Test
	public void getAllCategoryByPart() {
		JSONObject json = null;
		try {
			json = service.getAllCategoryByPart(1);
			logger.debug(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}