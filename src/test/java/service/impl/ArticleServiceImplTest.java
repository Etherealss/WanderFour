package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.enums.Partition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.bean.WritingBean;
import pojo.dto.WritingDto;
import pojo.po.Article;
import pojo.po.Writing;
import service.WritingService;
import common.util.TestUtil;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class ArticleServiceImplTest {
    private Logger logger = LoggerFactory.getLogger("testLogger");
    @Autowired
    WritingService<Article> service;

    @Test
    public void publishNewWriting() {
        Article writing = TestUtil.getDefaultArticlePo();
        Long id = null;
        try {
            id = service.publishNewWriting(writing);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(String.valueOf(id));
    }

    @Test
    public void getWriting() {
        WritingBean<Article> writing = null;
        try {
            writing = service.getWritingBean(1L, 2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(writing.toString());
    }

    @Test
    public void updateWriting() {
        Article writing = new Article();
        writing.setId(22L);
        writing.setPartition(Partition.LEARNING.val());
        writing.setAuthorId(4L);
        writing.setCategory(1);

        writing.setTitle("我是标题a");
        writing.setContent("主要内容aaaaaaaaaaaaa");
        writing.setLabel1("标签1");
        writing.setLabel2("标签2");
        ResultType resultType = service.updateWriting(writing);
        logger.debug(resultType.toString());
    }

    @Test
    public void deleteWriting() {
        ResultType resultType = service.deleteWriting(25L, 2L);
        logger.debug(resultType.toString());
    }

    @Test
    public void testGetWritingByTime() throws Exception {
        List<WritingDto<Article>> time = service.getWritingList(4L, 1, "time");
        JSONObject json = new JSONObject();
        json.put("articleList", time);
        logger.debug(json.toJSONString());
    }
}