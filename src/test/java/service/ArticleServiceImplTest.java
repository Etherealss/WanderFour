package service;

import common.enums.ResultState;
import common.enums.Partition;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.po.Article;

import java.util.Date;

public class ArticleServiceImplTest {

	private Logger logger = Logger.getLogger(ArticleServiceImplTest.class);
	WritingService<Article> service = ServiceFactory.getArticleService();

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
		ResultState resultState = service.publishNewWriting(writing);
		logger.debug(resultState);
	}

	@Test
	public void getWriting() {
		Article writing = service.getWriting(2L);
		logger.debug(writing);
	}

	@Test
	public void updateWriting() {
		Article writing = new Article();
		writing.setId(2L);
		writing.setPartition(Partition.LEARNING);
		writing.setAuthorId("1@qq.com");
		writing.setCategory("二级分类a");

		writing.setTitle("我是标题a");
		writing.setContent("主要内容aaaaaaaaaaaaa");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());
		ResultState resultState = service.updateWriting(writing);
		logger.debug(resultState);
	}

	@Test
	public void deleteWriting() {
		ResultState resultState = service.deleteWriting(18L,"1@qq.com");
		logger.debug(resultState);
	}
}