package service.impl;

import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import service.SensitiveService;

import java.util.Map;

public class SensitiveServiceImplTest {

	private Logger logger = Logger.getLogger(SensitiveServiceImplTest.class);
	SensitiveService service = ServiceFactory.getSensitiveService();

	@Test
	public void testInsertSensitiveWord() {
		service.insertSensitiveWord();
	}

	@Test
	public void testGetSensitiveWordsList() {
		Map<String, Object> sensitiveWordsList = service.getSensitiveWordsMap();
		logger.debug(sensitiveWordsList);
	}
}