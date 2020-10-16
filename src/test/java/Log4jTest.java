import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author 寒洲
 * @description
 * @date 2020/9/25
 */
public class Log4jTest {

	@Test
	public void log4jTest(){
		//开启log4j内置的日志几率
//		LogLog.setInternalDebugging(true);
		//初始化配置信息，不使用配置文件
//		BasicConfigurator.configure();
		Logger logger = Logger.getLogger(Log4jTest.class);
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
}
