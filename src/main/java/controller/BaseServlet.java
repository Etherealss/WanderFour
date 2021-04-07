package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
 * 提供预设的日志对象和返回信息报以及异常捕捉
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
			ex.printStackTrace();
			logger.error("（服务运行异常）" + ex.getMessage());
//			ex.printStackTrace();
//			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			resp.sendRedirect("505.html");
		}

		//执行完成之后回调
		afterService(req, resp);
	}

}
