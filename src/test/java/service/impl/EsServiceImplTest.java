package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.EsEnum;
import common.factory.DaoFactory;
import common.factory.ServiceFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.po.Article;
import service.CategoryService;
import service.EsService;

import java.sql.Connection;

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
		boolean b = service.deleteIndex(EsEnum.INDEX_NAME);
		logger.debug(b);
	}

	@Test
	public void testExistsIndex() throws Exception {
		boolean b = service.existsIndex(EsEnum.INDEX_NAME);
		logger.debug(b);
	}

	@Test
	public void testAddDoc() throws Exception {
		// 获取所有的分类Json
		CategoryService categoryService = ServiceFactory.getArticleService();
		// Connection连接会在categoryService中关闭而引起bug
		JSONObject allCategory = categoryService.getAllCategory();

		Connection conn = JdbcUtil.beginTransaction();
		WritingDao<Article> dao = DaoFactory.getArticleDao();

		for (int i = 5; i < 9; i++) {
			Article article = dao.getWritingById(conn, (long) i);
			String categoryName = (String) allCategory.get(article.getCategory());
			assert categoryName != null;
//			String s = service.addDoc(EsEnum.INDEX_NAME, article);
//			logger.debug(s);
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
	}
}