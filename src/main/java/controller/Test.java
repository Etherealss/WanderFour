package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description
 * @date 2020/11/25
 */
@WebServlet("/student")
public class Test extends BaseServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");
//		System.out.println("forward 前");
//		req.getRequestDispatcher("/Test2").forward(req, resp);
//		System.out.println("forward 后");
		System.out.println("redirect 前");
		resp.sendRedirect("/Test2");
		System.out.println("redirect 后");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost");
		String file = req.getParameter("file");
		logger.debug(file);
		String name = req.getParameter("name");
		logger.debug(name);
	}
}
