package socket;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Information;

import java.io.IOException;

/**
 * @author 寒洲
 * @description WebSocket 发送相关的 操作类
 * @date 2020/11/19
 */
public class SendOperator {
	private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");


	/**
	 * 如果目标在线，发送文本信息；不在线则缓存内存
	 * @param toUserId    发送目标
	 * @param information json格式数据，包含发送者id和信息内容
	 */
	public static void sendTxt(Long toUserId, Information information) throws Exception {
		try {
			ChatEndpoint user = UserConntionManeger.getUser(toUserId);
			if (user != null) {
				// 目标用户在线
				user.getSession().getBasicRemote().sendText(JSONObject.toJSONString(information));
			} else {
				// 目标用户不在线，添加缓存数据
				CacheMessageManager.add(toUserId, information);
			}

		} catch (IOException e) {
			logger.warn("发送信息失败：toUserId = '" + toUserId + "', messgae = " + information);
			throw e;
		}
	}
}
