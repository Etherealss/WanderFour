package service;

import common.enums.Partition;
import common.factory.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.po.Article;
import service.WritingService;

import java.util.Date;

public class WritingServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void publishNewWriting() {
		Article writing = new Article();
		writing.setPartition(Partition.LEARNING);
		writing.setAuthorId("1@qq.com");
		writing.setCategory("二级分类");

		writing.setTitle("我是标题");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());
		WritingService<Article> service = ServiceFactory.getArticleService();
		service.publishNewWriting(writing);
	}
}