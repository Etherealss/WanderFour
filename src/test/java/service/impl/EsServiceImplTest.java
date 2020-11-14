package service.impl;

import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import service.EsService;

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
		boolean b = service.deleteIndex(EsServiceImpl.INDEX_NAME);
		logger.debug(b);
	}

	@Test
	public void testExistsIndex() throws Exception {
		boolean b = service.existsIndex(EsServiceImpl.INDEX_NAME);
		logger.debug(b);
	}

	@Test
	public void testAddDoc() throws Exception {
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