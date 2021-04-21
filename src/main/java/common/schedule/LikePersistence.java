package common.schedule;

import common.enums.ApplicationConfig;
import common.factory.NamedThreadFactory;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 寒洲
 * @description 点赞持久化定时任务(中等速度)
 * @date 2020/10/18
 */
public class LikePersistence {

	private static Logger logger = Logger.getLogger(LikePersistence.class);

	/** 单元时间单位 */
	private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
	/** 首次执行的延时时间 */
	private static final long INITIAL_DELAY = 5;
	/** 定时执行的延迟时间 */
	private static final long PERIOD = ApplicationConfig.PERIOD_LIKE_PERSISTENCE;

	/**
	 * 定时任务
	 */
	private static ScheduledThreadPoolExecutor scheduled;

	/** 启动定时任务 */
	public static void runScheduled() {
		//创建线程池
		scheduled = new ScheduledThreadPoolExecutor(
				8, new NamedThreadFactory("点赞数据持久化"));
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的延迟时间
		scheduled.scheduleWithFixedDelay(new LikepersistenceRunnable(), INITIAL_DELAY, PERIOD, TIME_UNIT);
	}

	/**
	 * 关闭定时任务
	 * @throws Exception
	 */
	public static void shutDownScheduled() throws Exception {
		if (scheduled != null) {
			scheduled.shutdown();
		} else {
			throw new Exception("scheduled对象未创建！");
		}
	}
}
