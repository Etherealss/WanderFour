package service.impl;

import common.enums.EsEnum;
import common.enums.WritingType;
import common.factory.DaoFactory;
import common.factory.ServiceFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.bo.EsBo;
import pojo.bo.PageBo;
import pojo.po.Article;
import service.EsService;

import java.sql.Connection;
import java.util.List;

public class EsServiceImplTest {

	private Logger logger = Logger.getLogger(EsServiceImplTest.class);

	private EsService service = ServiceFactory.getEsService();

	@Test
	public void testCreateIndex() throws Exception {
		boolean writingIndex = service.createWritingIndex();
		logger.debug(writingIndex);
	}

	@Test
	public void testDeleteIndex() throws Exception {
		boolean b = service.deleteIndex(EsEnum.INDEX_NAME_WRITING);
		logger.debug(b);
	}

	@Test
	public void testExistsIndex() throws Exception {
		boolean b = service.existsIndex(EsEnum.INDEX_NAME_WRITING);
		logger.debug(b);
	}

	@Test
	public void testAddDoc() throws Exception {
		boolean b = service.existsIndex(EsEnum.INDEX_NAME_WRITING);
		if (!b){
			logger.error("索引不存在");
			service.createWritingIndex();
		}
		// 获取所有的分类Json
		Connection conn = JdbcUtil.beginTransaction();
		WritingDao<Article> dao = DaoFactory.getArticleDao();

		for (int i = 5; i < 9; i++) {
			Article article = dao.getWritingById(conn, (long) i);
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
		JdbcUtil.closeTransaction();
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
			logger.debug(esBo);
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
			logger.debug(esBo);
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