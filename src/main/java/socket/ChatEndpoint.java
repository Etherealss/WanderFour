package socket;

import com.alibaba.fastjson.JSONObject;
import common.enums.websocket.InfoType;
import common.util.WebSocketUtil;
import filter.SensitiveFilter;
import org.apache.log4j.Logger;
import pojo.Information;
import pojo.po.ChatRecord;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/18
 */
@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class ChatEndpoint {

	private Logger logger = Logger.getLogger(ChatEndpoint.class);
	private Session session;
	private HttpSession httpSession;

	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {

		// 将局部的session对象赋值给成员session
		this.session = session;

		// 获取在HttpSessionConfigurator中储存的HttpSession对象
		Object se = endpointConfig.getUserProperties().get(HttpSession.class.getName());
		if (se instanceof HttpSession) {
			HttpSession httpSession = (HttpSession) se;
			Long userId = WebSocketUtil.getFromId(httpSession);
			logger.trace("onOpen... userid = " + userId);
			this.httpSession = httpSession;
			//储存当前对象
			UserConntionManeger.putUser(userId, this);

			respCacheMessage(userId);
		} else {
			logger.error("httpSession设置错误");
			throw new Exception("httpSession设置错误");
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.debug("onMessage = " + message);
		/*
		 获取客户端发送来的消息数据，
		 并将数据转发给目标用户（toUsers）
		 */
		// 获取数据
		ChatRecord msg = JSONObject.parseObject(message, ChatRecord.class);

		SensitiveFilter filter = new SensitiveFilter();
		msg.setMessage(filter.checkString(msg.getMessage()));

		// 封装信息包
		Long fromUserId = WebSocketUtil.getFromId(httpSession);
		msg.setFromId(fromUserId);
		Information information = WebSocketUtil.getMessage(InfoType.SEND_TXT, false, msg);
		logger.debug("msg.getToId() = " + msg.getToId() + ", information = " + information);
		// 转发给对应用户
		try {
			SendOperator.sendTxt(msg.getToId(), information);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Session session, Throwable thr) {
		Long fromId = WebSocketUtil.getFromId(httpSession);
		logger.error("onError... userId = " + fromId + ", exception = " + thr.getMessage());
		thr.printStackTrace();
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		Long fromId = WebSocketUtil.getFromId(httpSession);
		logger.trace("onClose... userId = " + fromId);
		UserConntionManeger.removeUser(fromId);

	}

	/**
	 * 将缓存的消息发送给客户端
	 * @param userId
	 */
	private void respCacheMessage(Long userId) {
		List<Information> infos = CacheMessageManager.pop(userId);
		if (infos == null) {
			logger.trace("无缓存信息");
			return;
		}
		for (Information info : infos) {
			try {
				SendOperator.sendTxt(userId, info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Session getSession() {
		return session;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}
}
