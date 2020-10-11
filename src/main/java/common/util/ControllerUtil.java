package common.util;

import common.enums.Partition;
import pojo.po.Writing;

import javax.servlet.http.HttpServletRequest;

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
}
