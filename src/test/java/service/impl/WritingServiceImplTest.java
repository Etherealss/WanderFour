package service.impl;

import common.factory.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Writing;
import pojo.po.LearningArticle;
import service.WritingService;

import java.util.Date;

import static org.junit.Assert.*;

public class WritingServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void publishNewWriting() {
		LearningArticle writing = new LearningArticle();
		writing.setAuthorId("123@qq.com");
		writing.setCategory("二级分类");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		writing.setTitle("我是标题");
		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());
		WritingService<LearningArticle> service = ServiceFactory.getWritingService(new WritingServiceImpl<LearningArticle>());
		service.publishNewWriting(writing);
	}
}