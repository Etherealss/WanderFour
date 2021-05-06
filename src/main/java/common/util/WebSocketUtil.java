package common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.enums.websocket.InfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Information;
import pojo.po.ChatRecord;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * @author 寒洲
 * @description WebSocket工具
 * @date 2020/11/18
 */
public class WebSocketUtil {

	private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	/**
	 * 获取json字符串
	 * @param isSystemMessgae 是否为系统信息
	 * @param messageType     消息类型
	 * @param params
	 * @return json字符串
	 */
	public static String getMessage(boolean isSystemMessgae, InfoType messageType, Map<String, Object> params) {
		return JSON.toJSONString(new Information(messageType, isSystemMessgae, params));
	}

	/**
	 * 获取json字符串
	 * @param isSystemInfo   是否为系统信息
	 * @param infoType   消息类型
	 * @param chatRecord 会为message对象添加Date属性
	 * @return json字符串
	 */
	public static Information getMessage(InfoType infoType, boolean isSystemInfo, ChatRecord chatRecord) {
		chatRecord.setCreateTime(new Date());
		return new Information(infoType, isSystemInfo, chatRecord);
	}

	/**
	 * 获取json字符串
	 * @param isSystemInfo 是否为系统信息
	 * @param infoType 消息类型
	 * @param params   参数的键值对列表
	 * @return
	 */
	public static String getMessage(InfoType infoType, boolean isSystemInfo, Object... params) {
		Information res = new Information(infoType, isSystemInfo);

		String s = JSONObject.toJSONString(params);
		JSONObject data = JSON.parseObject(s);

		res.setData(data);
		return JSON.toJSONString(res);
	}

	/**
	 * 以Long类型 获取在HTTPSession中的userId
	 * @param httpSession
	 * @return
	 */
	public static Long getFromId(HttpSession httpSession) {
		if (httpSession == null){
			logger.warn("httpSession为null");
			return null;
		}
		Object attribute = httpSession.getAttribute(AttrEnum.LOGIN_SESSION_NAME);
		return Long.valueOf(attribute.toString());
	}
}
