package listener;

import common.enums.ApplicationConfig;
import common.others.EsProcessManager;
import common.schedule.LikePersistenceManager;
import common.util.EsUtil;
import common.util.JedisUtil;
import common.util.OsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author 寒洲
 * @description Tomcat监听
 * @date 2020/8/21
 */
@WebListener
public class TomcatListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("tomcat关闭......");
        //关闭Jedis连接池
        logger.info("关闭Jedis连接池.....");
        JedisUtil.closeJedisPool();
        //结束定时任务
        logger.info("结束定时任务.....");
        LikePersistenceManager.shutDownPersistenceDelayMinutes();

        // 关闭ElasticSearch
        // 是windows环境代表是本地环境，可以关闭服务
        if (OsUtil.isWindows() && ApplicationConfig.ES_SHUTDOWN && EsUtil.isEsHostConnected()) {
            // 已连接，关闭服务
            logger.info("关闭ES服务");
            EsProcessManager.destroyEsProcess();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        logger.info("tomcat初始化......");
        //启动定时任务，周期性持久化redis数据
        LikePersistenceManager.persistDelayMinutes();
//        launchEsService(arg0.getServletContext());

    }

    /**
     * 启动ES服务器
     */
    private void launchEsService(ServletContext servletContext) {
        // 启动ES服务
        boolean runSuccess = EsUtil.runEsService();
        if (runSuccess) {
            // 检查ES数据
            ApplicationContext applicationContext =
                    WebApplicationContextUtils.getWebApplicationContext(servletContext);
            assert applicationContext != null;
            EsProcessManager.esDataInit(applicationContext);
        }
    }
}

