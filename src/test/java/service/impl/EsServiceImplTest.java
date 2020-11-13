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
		service.createWritingIndex();
	}

	@Test
	public void testDeleteIndex() throws Exception {
	}

	@Test
	public void testExistsIndex() throws Exception {
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
}