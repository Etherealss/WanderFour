package controller;

import common.emun.ResultState;
import common.factory.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 检查账号是否存在
 * @date 2020/10/3
 */
@WebServlet("/CheckUserExistServlet")
public class CheckUserExistController extends BaseUserServlet{

	public void CheckUserExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");
		//获取service，检查email是否存在
		UserService us = ServiceFactory.getUserService();
		ResultState code = us.checkUserExist(email);
		info.put("code", code);
		responseToBrowser(resp);
	}
}
