package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import service.CategoryService;

import static org.junit.Assert.*;

public class CategoryServiceImplTest {

	private Logger logger = Logger.getLogger(CategoryServiceImplTest.class);
	private CategoryService service = ServiceFactory.getCategoryService();
	@Test
	public void getAllCategoryByPart() {
		JSONObject json = null;
		try {
			json = service.getAllCategoryByPart(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(json);
	}
}