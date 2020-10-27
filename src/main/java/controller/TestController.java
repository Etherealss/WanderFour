package controller;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
@WebServlet("/Test")
public class TestController extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String escape = req.getParameter("escape");
		String type = req.getParameter("type");
		String htmlCode = "<button type=\"button\" id=\"ajaxBtn\" onclick=\"changeLocation()\">登录</button>\n" +
				"    <script src=\"./JS/jQuery.js\"></script>\n" +
				"    <script>\n" +
				"        function changeLocation() {\n" +
				"            window.location.href = \"www.baidu.com\";\n" +
				"        }\n" +
				"    </script>";
		String jsCode = "<script>alert(\"123\")</script>";
		if ("html".equals(type)){
			if ("yes".equals(escape)){
				htmlCode = StringEscapeUtils.escapeHtml(htmlCode);
				System.out.println(htmlCode);
			} else {

			}
			resp.getWriter().write(htmlCode);

		}else if ("js".equals(type)){
			if ("yes".equals(escape)){
				jsCode = StringEscapeUtils.escapeJavaScript(jsCode);
				System.out.println(jsCode);
			} else {

			}
			resp.getWriter().write(jsCode);
		}
	}
}
