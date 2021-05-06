package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class SensitiveDaoTest {
	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Autowired
	private SensitiveDao dao;

	@Test
	public void testInsertSensitiveDao() throws Exception {
	}

	@Test
	public void testGetSensitiveWordsList() throws Exception {
		List<String> sensitiveWordsList = dao.getSensitiveWordsList();
		for (String s : sensitiveWordsList) {
			System.out.print(s);
		}
	}
}