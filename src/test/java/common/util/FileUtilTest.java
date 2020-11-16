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
		String textPath = "D:\\test.txt";
		Process process = FileUtil.runProcess(textPath);
		process.destroy();
		logger.debug(process.isAlive());
	}
}