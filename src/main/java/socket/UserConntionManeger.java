package socket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 寒洲
 * @description 在线用户管理类
 * @date 2020/11/18
 */
public class UserConntionManeger {

	private static Map<Long, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

	public static Map<Long, ChatEndpoint> getOnlineUsers() {
		return onlineUsers;
	}

	public static Set<Long> getOnlineUserId(){
		return onlineUsers.keySet();
	}

	/**
	 * 储存在线用户的ChatEndporint对象
	 * @param userId
	 * @param chatEndpoint
	 */
	public static void putUser(Long userId, ChatEndpoint chatEndpoint){
		onlineUsers.put(userId, chatEndpoint);
	}

	/**
	 * 获取在线用户的ChatEndporint对象
	 * @param userId
	 * @return
	 */
	public static ChatEndpoint getUser(Long userId){
		return onlineUsers.get(userId);
	}

	public static void removeUser(Long userId){
		onlineUsers.remove(userId);
	}
}
