package common.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 寒洲
 * @description 点赞实例化 定时任务控制器
 * @date 2020/10/21
 */
public class LikePersistenceManager {

	private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	/**
	 * 启动定时任务
	 */
	public static void persistDelayMinutes(){
		LikePersistence.runScheduled();
	}

	/**
	 * 关闭定时任务
	 */
	public static void shutDownPersistenceDelayMinutes(){
		try {
			LikePersistence.shutDownScheduled();
		} catch (Exception e) {
			logger.error("关闭定时任务时异常", e);
		}
	}

}
