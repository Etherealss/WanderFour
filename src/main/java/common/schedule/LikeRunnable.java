package common.schedule;

import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import service.LikeService;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/21
 */
public class LikeRunnable implements Runnable{
	private Logger logger = Logger.getLogger(LikeRunnable.class);

	@Override
	public void run() {
		logger.trace("[" + Thread.currentThread().getName() + "]线程运行(run)，redis持久化！");
		LikeService service = ServiceFactory.getLikeService();
		try {
			//TODO 可以了解一下 消息队列
			service.persistLikeCount();
			service.persistLikeRecord();
		} catch (Exception e) {
			logger.error("[" + Thread.currentThread().getName() + "]线程 redis持久化异常！");
			e.printStackTrace();
		}
	}
}
