package service.impl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class CategoryServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private CategoryService service;

    @Test
    public void getAllCategoryByPart() {
        JSONObject json = null;
        try {
            json = service.getAllCategoryByPart(1);
            logger.debug(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}