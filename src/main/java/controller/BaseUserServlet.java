package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class BaseUserServlet extends HttpServlet {
	/**
	 * 日志
	 */
	protected final Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 返回给客户端的信息包
	 */
	protected Map<String, Object> info = new HashMap<>();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("BaseUserServlet执行");
		try {
			// 获取请求标识
			String methodName = request.getParameter("method");
			logger.info("method = " + methodName);
			// 获取指定类的字节码对象
			// 这里的this指的是继承BaseServlet对象
			Class<? extends BaseUserServlet> clazz = this.getClass();
			// 通过类的字节码对象获取方法的字节码对象
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 让方法执行
			method.invoke(this, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void responseToBrowser(HttpServletResponse response){
		//将map转为JSON
		ObjectMapper mapper = new ObjectMapper();
		//发送给客户端
		try {
			mapper.writeValue(response.getWriter(), info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
