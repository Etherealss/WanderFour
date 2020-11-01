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
	 * html代码转义
	 * @param source
	 * @return
	 */
	public static String htmlEncode(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
				case '<':
					buffer.append("&lt;");
					break;
				case '>':
					buffer.append("&gt;");
					break;
				case '&':
					buffer.append("&amp;");
					break;
				case '"':
					buffer.append("&quot;");
					break;
				case 10:
				case 13:
					break;
				default:
					buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}

	public static void htmlEncode(Article a) {
		a.setTitle(htmlEncode(a.getTitle()));
		a.setLabel1(htmlEncode(a.getLabel1()));
		a.setLabel2(htmlEncode(a.getLabel2()));
		a.setLabel3(htmlEncode(a.getLabel3()));
		a.setLabel4(htmlEncode(a.getLabel4()));
		a.setLabel5(htmlEncode(a.getLabel5()));
	}

	public static void htmlEncode(Posts p) {
		p.setTitle(htmlEncode(p.getTitle()));
//		p.setContent(htmlEncode(p.getContent()));
		p.setLabel1(htmlEncode(p.getLabel1()));
		p.setLabel2(htmlEncode(p.getLabel2()));
		p.setLabel3(htmlEncode(p.getLabel3()));
		p.setLabel4(htmlEncode(p.getLabel4()));
		p.setLabel5(htmlEncode(p.getLabel5()));
	}

	public static void htmlEncode(Comment p) {
		p.setContent(htmlEncode(p.getContent()));
	}


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
//		a.setContent(StringEscapeUtils.escapeJavaScript(String.valueOf(a.getContent())));
		a.setTitle(StringEscapeUtils.escapeJavaScript(a.getTitle()));
		a.setLabel1(StringEscapeUtils.escapeJavaScript(a.getLabel1()));
		a.setLabel2(StringEscapeUtils.escapeJavaScript(a.getLabel2()));
		a.setLabel3(StringEscapeUtils.escapeJavaScript(a.getLabel3()));
		a.setLabel4(StringEscapeUtils.escapeJavaScript(a.getLabel4()));
		a.setLabel5(StringEscapeUtils.escapeJavaScript(a.getLabel5()));
	}

	/**
	 * 防止js注入
	 * @param p
	 * @return
	 */
	public static void ensureJsSafe(Posts p) {
		//TODO Writing没有对于的setter和getter，没办法通过泛型封装 待优化
		p.setContent(StringEscapeUtils.escapeJavaScript(p.getContent()));
		p.setTitle(StringEscapeUtils.escapeJavaScript(p.getTitle()));
		p.setLabel1(StringEscapeUtils.escapeJavaScript(p.getLabel1()));
		p.setLabel2(StringEscapeUtils.escapeJavaScript(p.getLabel2()));
		p.setLabel3(StringEscapeUtils.escapeJavaScript(p.getLabel3()));
		p.setLabel4(StringEscapeUtils.escapeJavaScript(p.getLabel4()));
		p.setLabel5(StringEscapeUtils.escapeJavaScript(p.getLabel5()));
	}

	/**
	 * 防止js注入
	 * @param c
	 * @return
	 */
	public static void ensureJsSafe(Comment c) {
		c.setContent(StringEscapeUtils.escapeJavaScript(c.getContent()));
	}
}
