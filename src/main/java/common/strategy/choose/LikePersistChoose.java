package common.strategy.choose;

import common.schedule.LikePersistencebyMinutes;
import common.schedule.LikePersistenceBySecond;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/21
 */
public class LikePersistChoose {

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
			e.printStackTrace();
		}
	}
}
