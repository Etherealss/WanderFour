package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Information;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/19
 */
public class CacheMessageManager {

	private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	/**
	 * 缓存未发送的信息，键为发送对象的userId，值为1~n个消息列表
	 */
	private static Map<Long, List<Information>> cacheMessage = new ConcurrentHashMap<>();

	/**
	 * 添加缓存的信息
	 * @param toUserId 发送对象的userId
	 * @param information
	 */
	public static void add(Long toUserId, Information information) {
		logger.trace("添加缓存信息：toUserId = " + toUserId);
		List<Information> infos = cacheMessage.get(toUserId);
		if (infos != null) {
			infos.add(information);
		} else {
			// 不存在缓存信息，则新建缓存
			ArrayList<Information> newInfos = new ArrayList<>();
			newInfos.add(information);
			cacheMessage.put(toUserId, newInfos);
		}
	}

	/**
	 * 获取并移除缓存
	 * @param userId
	 * @return
	 */
	public static List<Information> pop(Long userId){
		// 获取并移除
		List<Information> infos = cacheMessage.get(userId);
		if (infos != null) {
			cacheMessage.remove(userId);
		}
		return infos;
	}


}
