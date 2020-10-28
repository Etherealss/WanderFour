package common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
public class SecurityUtil {

	private static Logger logger = Logger.getLogger(SecurityUtil.class);

	/**
	 * json防止HTML注入
	 * @param json
	 * @return
	 */
	public static JSONObject ensureHtmlSafe(JSONObject json) {
		JSONObject resultJson = new JSONObject();
		//遍历键值对
		Map<String, Object> innerMap = json.getInnerMap();
		for (Map.Entry<String, Object> vo : innerMap.entrySet()) {
			Object value = vo.getValue();
			//保留键，对值进行转义，防止HTML注入
			String s = StringEscapeUtils.escapeHtml(String.valueOf(value));
			logger.debug(vo.getKey() + "  " + s);
			//再次包装
			resultJson.put(vo.getKey(), s);
		}
		//TODO 防止了HTML注入
		return resultJson;
	}

	/**
	 * json防止js注入
	 * @param json
	 * @return
	 */
	public static JSONObject ensureJsSafe(JSONObject json) {
		JSONObject resultJson = new JSONObject();
		//遍历键值对
		Map<String, Object> innerMap = json.getInnerMap();
		for (Map.Entry<String, Object> vo : innerMap.entrySet()) {
			Object value = vo.getValue();
			//保留键，对值进行转义，防止JS注入
			String s = StringEscapeUtils.escapeJavaScript(String.valueOf(value));
			//再次包装
			resultJson.put(vo.getKey(), s);
		}
		return resultJson;
	}
}
