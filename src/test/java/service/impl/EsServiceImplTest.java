package service.impl;

import common.enums.EsEnum;
import common.enums.WritingType;
import dao.ArticleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.bo.EsBo;
import pojo.bo.PageBo;
import pojo.po.Article;
import service.EsService;

import java.sql.Connection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class EsServiceImplTest {

	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Autowired
	private EsService service;
	@Autowired
	private ArticleDao articleDao;

	@Test
	public void testCreateIndex() throws Exception {
		boolean writingIndex = service.createWritingIndex();
		logger.debug(String.valueOf(writingIndex));
	}

	@Test
	public void testDeleteIndex() throws Exception {
		boolean b = service.deleteIndex(EsEnum.INDEX_NAME_WRITING);
		logger.debug(String.valueOf(b));
	}

	@Test
	public void testExistsIndex() throws Exception {
		boolean b = service.existsIndex(EsEnum.INDEX_NAME_WRITING);
		logger.debug(String.valueOf(b));
	}

	@Test
	public void testAddDoc() throws Exception {
		boolean b = service.existsIndex(EsEnum.INDEX_NAME_WRITING);
		if (!b){
			logger.error("索引不存在");
			service.createWritingIndex();
		}
		// 获取所有的分类Json

		for (int i = 5; i < 9; i++) {
			Article article = articleDao.getWritingById((long) i);
			EsBo bo = new EsBo();
			bo.setAuthorId(article.getAuthorId());
			bo.setTitle(article.getTitle());
			bo.setContent(article.getContent());
			bo.setCreateTime(article.getCreateTime());
			bo.setUpdateTime(article.getUpdateTime());
			bo.setWritingId(article.getId());
			bo.setWritingType(WritingType.ARTICLE.val());
			String s = service.addDoc(EsEnum.INDEX_NAME_WRITING, bo);
		}
	}

	@Test
	public void testDeleteDoc() throws Exception {
	}

	@Test
	public void testUpdateDoc() throws Exception {
	}

	@Test
	public void testBulkDoc() throws Exception {
	}

	@Test
	public void testCreateWritingIndex() throws Exception {
	}

	@Test
	public void testSearchByHighLigh() throws Exception {
		PageBo<EsBo> page = service.searchByHighLigh("怎么学好大学英语", 0, 10);
		logger.debug("totalCount = " + page.getTotalCount() + "\n" +
				"totalPage = " + page.getTotalPage() + "\n" +
				"currentPage = " + page.getCurrentPage()
		);
		List<EsBo> list = page.getList();
		for (EsBo esBo : list) {
			logger.debug(esBo.toString());
		}
	}

	@Test
	public void testCheckWritingsExist() throws Exception {
	}

	@Test
	public void testInitWritingDocs() throws Exception {
	}

	@Test
	public void testSearchByPrefix() throws Exception {
		List<EsBo> list = service.searchByPrefix("数学", 1, 10);
		for (EsBo esBo : list) {
			logger.debug(esBo.toString());
		}
	}

	@Test
	public void testQuerySuggestion() throws Exception {
		List<String> list = service.querySuggestion("大学", EsEnum.INDEX_NAME_WRITING, 10);
		for (String esBo : list) {
			logger.debug(esBo);
		}
	}
}