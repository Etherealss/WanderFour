package common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;
import pojo.po.Writing;

import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
public class SecurityUtil {

    private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

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

//	public static void htmlEncode(Article a) {
//		a.setTitle(htmlEncode(a.getTitle()));
//		a.setLabel1(htmlEncode(a.getLabel1()));
//		a.setLabel2(htmlEncode(a.getLabel2()));
//		a.setLabel3(htmlEncode(a.getLabel3()));
//		a.setLabel4(htmlEncode(a.getLabel4()));
//		a.setLabel5(htmlEncode(a.getLabel5()));
//	}
//
//	public static void htmlEncode(Posts p) {
//		p.setTitle(htmlEncode(p.getTitle()));
////		p.setContent(htmlEncode(p.getContent()));
//	}

    public static void htmlEncode(Writing writing) {
        writing.setTitle(htmlEncode(writing.getTitle()));
        writing.setLabel1(htmlEncode(writing.getLabel1()));
        writing.setLabel2(htmlEncode(writing.getLabel2()));
        writing.setLabel3(htmlEncode(writing.getLabel3()));
        writing.setLabel4(htmlEncode(writing.getLabel4()));
        writing.setLabel5(htmlEncode(writing.getLabel5()));
    }

    public static void htmlEncode(Posts writing) {
        writing.setContent(writing.getContent());
        htmlEncode((Writing) writing);
    }

    public static void htmlEncode(Comment c) {
        c.setContent(htmlEncode(c.getContent()));
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
        escapeJavaScript(a);
    }

    /**
     * 防止js注入
     * @param p
     * @return
     */
    public static void ensureJsSafe(Posts p) {
        p.setContent(StringEscapeUtils.escapeJavaScript(p.getContent()));
        escapeJavaScript(p);
    }

    /**
     * 防止js注入
     * @param w
     * @return
     */
    private static void escapeJavaScript(Writing w) {
        w.setTitle(StringEscapeUtils.escapeJavaScript(w.getTitle()));
        w.setLabel1(StringEscapeUtils.escapeJavaScript(w.getLabel1()));
        w.setLabel2(StringEscapeUtils.escapeJavaScript(w.getLabel2()));
        w.setLabel3(StringEscapeUtils.escapeJavaScript(w.getLabel3()));
        w.setLabel4(StringEscapeUtils.escapeJavaScript(w.getLabel4()));
        w.setLabel5(StringEscapeUtils.escapeJavaScript(w.getLabel5()));
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
