package common.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Md5UtilsTest {

	private Logger logger = Logger.getLogger(Md5UtilsTest.class);


	@Test
	public void testMd5Encode() throws Exception {
		String s = Md5Utils.md5Encode("333333@qq.com123123");
		logger.debug(s);
		logger.debug(s.length());
	}
}