package common.util;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileUtilTest {

	private Logger logger = Logger.getLogger(FileUtilTest.class);


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
		logger.debug(process.isAlive());
	}
}