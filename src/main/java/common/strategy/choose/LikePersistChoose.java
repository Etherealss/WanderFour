package common.strategy.choose;

import common.schedule.LikePersistencebyMinutes;
import common.schedule.LikePersistenceBySecond;
import org.apache.log4j.Logger;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/21
 */
public class LikePersistChoose {

	private static Logger logger = Logger.getLogger(LikePersistChoose.class);

	/**
	 * 启动定时任务
	 */
	public static void persistDelayMinutes(){
		LikePersistencebyMinutes.runScheduled();
	}

	/**
	 * 关闭定时任务
	 */
	public static void shutDownPersistenceDelayMinutes(){
		try {
			LikePersistencebyMinutes.shutDownScheduled();
		} catch (Exception e) {
			logger.error("关闭定时任务时异常");
			e.printStackTrace();
		}
	}

	/**
	 * 启动间隔时间端的定时任务（用于测试）
	 */
	public static void persistDelaySecond(){
		LikePersistenceBySecond.runScheduled();
	}

	/**
	 * 关闭定时任务
	 */
	public static void shutDownPersistenceDelaySecond(){
		try {
			LikePersistenceBySecond.shutDownScheduled();
		} catch (Exception e) {
			logger.error("关闭定时任务时异常");
			e.printStackTrace();
		}
	}
}
