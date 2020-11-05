package service.impl;

import common.bean.SensitiveNode;
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
		Map<Character, SensitiveNode> sensitiveWordsList = service.getSensitiveWordsMap();
		logger.debug(sensitiveWordsList);
	}

	@Test
	public void testGetSensitiveWordsMap2() throws Exception {
		SensitiveServiceImpl service = new SensitiveServiceImpl();
		Map<Character, SensitiveNode> sensitiveWordsMap2 = service.getSensitiveWordsMap();
		logger.debug(sensitiveWordsMap2);
	}
}