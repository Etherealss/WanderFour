package common.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Md5UtilsTest {

	private Logger logger = Logger.getLogger(Md5UtilsTest.class);


	@Test
	public void testMd5Encode() throws Exception {
		String email = "159159@qq.com";
		String pw = "58cf703f664397ec4f0ac359b84b565c";
		String s = Md5Utils.md5Encode(email + pw);
		logger.debug(s);
		logger.debug(s.length());
		logger.debug(s.equals("35sfplar82oqbqr2qig4od6u6l"));
	}
}