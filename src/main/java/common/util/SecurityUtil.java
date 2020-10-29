package common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;

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

	/**
	 * 防止js注入
	 * @param a
	 * @return
	 */
	public static void ensureJsSafe(Article a) {
		//TODO 优化
		a.setContent(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getContent())));
		a.setTitle(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getTitle())));
		a.setLabel1(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getLabel1())));
		a.setLabel2(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getLabel2())));
		a.setLabel3(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getLabel3())));
		a.setLabel4(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getLabel4())));
		a.setLabel5(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getLabel5())));
	}

	/**
	 * 防止js注入
	 * @param p
	 * @return
	 */
	public static void ensureJsSafe(Posts p) {
		//TODO Writing没有对于的setter和getter，没办法通过泛型封装 待优化
		p.setContent(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getContent())));
		p.setTitle(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getTitle())));
		p.setLabel1(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getLabel1())));
		p.setLabel2(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getLabel2())));
		p.setLabel3(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getLabel3())));
		p.setLabel4(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getLabel4())));
		p.setLabel5(StringEscapeUtils.escapeJavaScript(String.valueOf(p.getLabel5())));
	}

	/**
	 * 防止js注入
	 * @param c
	 * @return
	 */
	public static void ensureJsSafe(Comment c) {
		c.setContent(StringEscapeUtils.escapeJavaScript(String.valueOf(c.getContent())));
	}
}
