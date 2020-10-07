package controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class BaseController extends HttpServlet {
	protected Logger log = Logger.getLogger(BaseController.class);
	protected void beforeService(HttpServletRequest request, HttpServletResponse response) {

	}
	protected void afterService(HttpServletRequest request,HttpServletResponse response) {

	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//执行前
		beforeService(req,resp);
		//执行 doGet 等 方法
		try{
			super.service(req, resp);
		}catch (Exception ex) {
			log.error("服务运行异常" + ex.getMessage());
			resp.sendRedirect("505.html");
		}
		//执行完成之后回调
		afterService(req,resp);
	}

}
