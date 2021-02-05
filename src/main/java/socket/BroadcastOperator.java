package socket;

import common.enums.websocket.InfoType;
import common.util.WebSocketUtil;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author 寒洲
 * @description WebSocket广播
 * @date 2020/11/18
 */
public class BroadcastOperator {

	private static Logger logger = Logger.getLogger(BroadcastOperator.class);

	public static void newUserLogin(Long userId) {
		String message = WebSocketUtil.getMessage(
				InfoType.NEW_USER_ENTER,true, "userid", userId);
		// 广播
		BroadcastOperator.broadcastToAllOnlineUser(message);

	}

	/**
	 * 向所有在线用户广播
	 * @param message 广播的信息
	 */
	private static void broadcastToAllOnlineUser(String message) {
		try {
			Map<Long, ChatEndpoint> onlineUsers = UserConntionManeger.getOnlineUsers();
			for (Map.Entry<Long, ChatEndpoint> entry : onlineUsers.entrySet()) {
				// entry.getValue()获取ChatEndpoint对象，再获取其中的Session对象，发送message信息

				entry.getValue().getSession().getBasicRemote().sendText(message);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
