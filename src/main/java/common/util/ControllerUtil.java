package common.util;

import com.alibaba.fastjson.JSONObject;
import pojo.po.Writing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class ControllerUtil {

	public static void setWritingWithData(HttpServletRequest req, Writing p) {
		//TODO 字符串还是数字
		String partition = req.getParameter("partition");
		String category = req.getParameter("category");
		String authorId = req.getParameter("authorId");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String label1 = req.getParameter("label1");
		String label2 = req.getParameter("label2");
		String label3 = req.getParameter("label3");
		String label4 = req.getParameter("label4");
		String label5 = req.getParameter("label5");
		// 赋值
		p.setPartitionStr(partition);
		p.setCategory(category);
		p.setAuthorId(authorId);
		p.setTitle(title);
		p.setContent(content);
		p.setLabel1(label1);
		p.setLabel2(label2);
		p.setLabel3(label3);
		p.setLabel4(label4);
		p.setLabel5(label5);
	}

	/**
	 * 传输返回给浏览器的json包
	 * @param response
	 * @param jsonObject 返回给浏览器的json包
	 */
	public static void responseToBrowser(HttpServletResponse response, JSONObject jsonObject) {
		//将map转为JSON
		//发送给客户端
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
