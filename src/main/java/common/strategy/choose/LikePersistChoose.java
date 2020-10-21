package common.strategy.choose;

import common.schedule.LikePersistencebyMinutes;
import common.schedule.LikePersistenceBySecond;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/21
 */
public class LikePersistChoose {

	public static void persistWithFixedDelayMinutes(){
		LikePersistencebyMinutes.runScheduled();
	}

	public static void persistWithFixedDelaySecond(){
		LikePersistenceBySecond.runScheduled();
	}
}
