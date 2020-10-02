package controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
@WebServlet("/BaseUserServlet")
public class BaseUserServlet extends HttpServlet {

	private final Logger logger = Logger.getLogger(BaseUserServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("BaseUserServlet执行");
		try {
			// 获取请求标识
			String methodName = request.getParameter("method");
			// 获取指定类的字节码对象
			Class<? extends BaseUserServlet> clazz = this.getClass();//这里的this指的是继承BaseServlet对象
			// 通过类的字节码对象获取方法的字节码对象
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 让方法执行
			method.invoke(this, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
