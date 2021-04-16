import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 寒洲
 * @description
 * @date 2020/9/25
 */
public class Log4jTest {
	Logger logger = Logger.getLogger(Log4jTest.class);

	@Test
	public void log4jTest(){
		//开启log4j内置的日志几率
//		LogLog.setInternalDebugging(true);
		//初始化配置信息，不使用配置文件
//		BasicConfigurator.configure();
		logger.fatal("fatal");
		logger.error("error");
		logger.warn("warn");
		logger.info("info");
		logger.debug("debug");
		//追踪信息，几率程序所有流程信息
		logger.trace("trace");

		System.out.println("————————————————————————————————————");
		try {
			int i = 1/0;
		}catch (Exception e){
//			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Test
	public void testError() throws Exception {
		try {
			f();
		} catch (Exception e) {
			logger.fatal("错误", e);
		}
	}

	private void f() {
		String s = h();
		int len = s.length();
	}

	private String h() {
		return null;
	}
}
