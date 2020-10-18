package service;

import common.enums.ResultType;
import common.enums.Partition;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.po.Article;
import util.TestUtil;

import java.util.Date;

public class ArticleServiceImplTest {

	private Logger logger = Logger.getLogger(ArticleServiceImplTest.class);
	WritingService<Article> service = ServiceFactory.getArticleService();

	@Test
	public void publishNewWriting() {
		Article writing = TestUtil.getDefaultArticlePo();
		ResultType resultType = service.publishNewWriting(writing);
		logger.debug(resultType);
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
		writing.setPartition(Partition.LEARNING.val());
		writing.setAuthorId(1L);
		writing.setCategory("二级分类a");

		writing.setTitle("我是标题a");
		writing.setContent("主要内容aaaaaaaaaaaaa");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());
		ResultType resultType = service.updateWriting(writing);
		logger.debug(resultType);
	}

	@Test
	public void deleteWriting() {
		ResultType resultType = service.deleteWriting(18L,2L);
		logger.debug(resultType);
	}
}