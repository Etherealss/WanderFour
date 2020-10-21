package common.schedule;

import common.factory.NamedThreadFactory;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 寒洲
 * @description 点赞持久化定时任务(快速，用于测试)
 * @date 2020/10/18
 */
public class LikePersistenceBySecond {

	/** 单元时间单位 */
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	/** 首次执行的延时时间 */
	private static final long INITIAL_DELAY = 15;
	/** 定时执行的延迟时间（15秒一次，用于测试） */
	private static final long PERIOD = 15;

	/** 启动定时任务 */
	public static void runScheduled() {
		//创建线程池
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(
				8, new NamedThreadFactory("点赞数据持久化"));
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的延迟时间
		scheduled.scheduleWithFixedDelay(new LikeRunnable(), INITIAL_DELAY, PERIOD, TIME_UNIT);
	}
}
