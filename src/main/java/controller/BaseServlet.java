package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 寒洲
 * @description Servlet基类
 * 利用反射调用Controller的方法
 * 且提供预设的日志对象和返回信息报以及异常捕捉
 * @date 2020/10/2
 */
public class BaseServlet extends HttpServlet {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected void beforeService(HttpServletRequest request, HttpServletResponse response) {

	}

	protected void afterService(HttpServletRequest request, HttpServletResponse response) {

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//执行前
		beforeService(req, resp);
		//执行doGet、doPost等方法
		try {
			super.service(req, resp);
		} catch (Exception ex) {
			logger.error("（服务运行异常）" + ex.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			resp.sendRedirect("505.html");
		}
		//执行完成之后回调
		afterService(req, resp);
	}

	/**
	 * 获取用于反射的方法
	 * @param request
	 * @return
	 */
	private Method getMethod(HttpServletRequest request) {
		// 获取请求标识
		String methodName = request.getParameter("method");
		logger.debug("method = " + methodName);
		// 获取指定类的字节码对象
		// 这里的this指的是继承BaseServlet对象
		Class<? extends BaseServlet> clazz = this.getClass();
		// 通过类的字节码对象获取方法的字节码对象
		Method method = null;
		try {
			// 因为Servlet是单例多线程，所以map不适合作为成员变量，
			// 此处转为参数传给所有方法
			method = clazz.getDeclaredMethod(methodName, Map.class, HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert method != null;
		return method;
	}

//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Method method = getMethod(request);
//		try {
//			// 让方法执行
//			Map<String, Object> info = new Hashtable<>();
//			logger.trace(method.getName() + "()执行");
//			method.invoke(this, info, request, response);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//	}
}
