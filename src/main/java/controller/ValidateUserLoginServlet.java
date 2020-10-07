package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 检查用户的登录状态（已登录/游客）
 * @date 2020/10/4
 */
@WebServlet("/ValidateUserLoginServlet")
public class ValidateUserLoginServlet extends BasePostServlet {

	/**
	 * 检查用户的登录状态（已登录/游客）
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateUserLoginState(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object email = req.getServletContext().getAttribute("email");
		if (email == null) {
			//未登录，发送信息给前端
			/*
			这里本来是用response的重定向直接去往warning.html的
			但是用ajax访问后端不能用这种方式跳转，要用ajax跳转
			 */
			resp.getWriter().println("false");
			logger.trace("检查用户的登录状态 未登录");
		}else{
			//已登录，发送email给前端验证
			resp.getWriter().println(email);
			logger.trace("检查用户的登录状态 已登录：" + email);
		}
	}
}
