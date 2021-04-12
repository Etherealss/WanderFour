package common.schedule;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.LikeService;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/21
 */
public class LikeRunnable implements Runnable {
	private Logger logger = Logger.getLogger(LikeRunnable.class);

	private LikeService likeService;
	{
		ApplicationContext ac =
				new ClassPathXmlApplicationContext("spring/spring-config.xml");
		likeService =
				(LikeService) ac.getBean("likeService");
	}

	@Override
	public void run() {
		logger.trace("[" + Thread.currentThread().getName() + "]线程运行(run)，redis持久化！");
		try {
			//TODO 了解 消息队列
			//TODO 点赞时持久化待优化：获取记录时统计点赞数，并将关系储存在数据库，之后根据统计数更新字段
			likeService.persistLikeCount();
			likeService.persistLikeRecord();
		} catch (Exception e) {
			logger.error("[" + Thread.currentThread().getName() + "]线程 redis持久化异常！");
			e.printStackTrace();
		}
	}
}
