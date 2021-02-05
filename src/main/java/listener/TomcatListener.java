package listener;

import common.others.EsProcessManager;
import common.strategy.choose.LikePersistChoose;
import common.util.EsUtil;
import common.util.FileUtil;
import common.util.JedisUtil;
import controller.UserEnterController;
import org.apache.log4j.Logger;
import service.EsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

/**
 * @author 寒洲
 * @description Tomcat监听
 * @date 2020/8/21
 */
@WebListener
public class TomcatListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(TomcatListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("tomcat关闭......");
		//关闭Jedis连接池
		logger.info("关闭Jedis连接池.....");
		JedisUtil.closeJedisPool();
		//结束定时任务
		logger.info("结束定时任务.....");
		LikePersistChoose.shutDownPersistenceDelayMinutes();

		// 关闭ElasticSearch
		if (EsUtil.isEsHostConnected()){
			// 已连接，关闭服务
			logger.info("关闭ES服务");
			EsProcessManager.destroyEsProcess();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("tomcat初始化......");
		//启动定时任务，周期性持久化redis数据
		LikePersistChoose.persistDelayMinutes();
//		launchEsService();

	}

	/**
	 * 启动ES服务器
	 */
	private void launchEsService() {
		// 启动ES服务
		boolean runSuccess = EsUtil.runEsService();
		if (runSuccess){
			// 检查ES数据
			EsProcessManager.esDataInit();
		} else {
			logger.warn("ES数据未初始化");
		}
	}
}

