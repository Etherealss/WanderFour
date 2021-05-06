import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 寒洲
 * @description
 * @date 2020/9/25
 */
public class Log4j2Test {
	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void log4jTest(){
		//开启log4j内置的日志几率
//		LogLog.setInternalDebugging(true);
		//初始化配置信息，不使用配置文件
//		BasicConfigurator.configure();
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
			logger.error("错误", e);
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
