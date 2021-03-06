package service.impl;

import common.structure.SensitiveNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.SensitiveService;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class SensitiveServiceImplTest {

	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Autowired
	private SensitiveService service;

	@Test
	public void testInsertSensitiveWord() {
		String path = "C:\\Users\\寒洲\\Desktop\\sensitive-words-master\\暴恐.txt";
		service.insertSensitiveWord(2, path);
	}

	@Test
	public void testGetSensitiveWordsList() {
		Map<Character, SensitiveNode> sensitiveWordsList = service.getSensitiveWordsMap();
		logger.debug(sensitiveWordsList.toString());
	}

}