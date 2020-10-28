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
		String path = "C:\\Users\\寒洲\\Desktop\\sensitive-words-master\\暴恐.txt";
		service.insertSensitiveWord(2, path);
	}

	@Test
	public void testGetSensitiveWordsList() {
		Map<String, Object> sensitiveWordsList = service.getSensitiveWordsMap();
		logger.debug(sensitiveWordsList);
	}
}