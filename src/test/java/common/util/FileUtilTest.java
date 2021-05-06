package common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilTest {

	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void testGetFileStream() throws Exception {
	}

	@Test
	public void testGetImgByBase64() throws Exception {
	}

	@Test
	public void testRunEsService() throws Exception {
		String path = EsUtil.getEsPath();
//		String path = "D:\\test.txt";
		logger.debug(path);
		Process process = FileUtil.runProcess(path);
		logger.debug(String.valueOf(process.isAlive()));
	}
}