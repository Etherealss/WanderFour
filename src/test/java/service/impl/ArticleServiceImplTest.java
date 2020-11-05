package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.enums.Partition;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.bean.WritingBean;
import pojo.dto.WritingDto;
import pojo.po.Article;
import service.WritingService;
import common.util.TestUtil;

import java.util.List;

public class ArticleServiceImplTest {

	private Logger logger = Logger.getLogger(ArticleServiceImplTest.class);
	WritingService<Article> service = ServiceFactory.getArticleService();

	@Test
	public void publishNewWriting() {
		Article writing = TestUtil.getDefaultArticlePo();
		Long id = null;
		try {
			id = service.publishNewWriting(writing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(id);
	}

	@Test
	public void getWriting() {
		WritingBean<Article> writing = null;
		try {
			writing = service.getWriting(1L, 2L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(writing);
	}

	@Test
	public void updateWriting() {
		Article writing = new Article();
		writing.setId(68L);
		writing.setPartition(Partition.LEARNING.val());
		writing.setAuthorId(1L);
		writing.setCategory(1);

		writing.setTitle("我是标题a");
		writing.setContent("主要内容aaaaaaaaaaaaa");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		ResultType resultType = null;
		try {
			resultType = service.updateWriting(writing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(resultType);
	}

	@Test
	public void deleteWriting() {
		ResultType resultType = null;
		try {
			resultType = service.deleteWriting(18L,2L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(resultType);
	}

	@Test
	public void testGetWritingByTime() throws Exception {
		List<WritingDto<Article>> time = service.getWritingList(4L, 1, "time");
		JSONObject json = new JSONObject();
		json.put("articleList", time);
		logger.debug(json);
	}
}