package common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5UtilsTest {

	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void testMd5Encode() throws Exception {
		String email = "159159@qq.com";
		String pw = "58cf703f664397ec4f0ac359b84b565c";
		String s = Md5Utils.md5Encode(email + pw);
		logger.debug(s);
		logger.debug(String.valueOf(s.length()));
		logger.debug(String.valueOf(s.equals("35sfplar82oqbqr2qig4od6u6l")));
	}
}