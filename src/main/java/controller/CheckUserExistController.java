package controller;

import common.enums.ResultState;
import common.factory.ServiceFactory;
import common.util.ControllerUtil;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author 寒洲
 * @description 检查账号是否存在
 * @date 2020/10/3
 */
@WebServlet("/CheckUserExistServlet")
public class CheckUserExistController extends BaseServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		//获取service，检查email是否存在
		UserService us = ServiceFactory.getUserService();
		ResultState state = us.checkUserExist(email);
		logger.debug("email = " + email + ", state = " + state);
		Map<String, Object> info = new Hashtable<>();
		info.put("state", state);
		ControllerUtil.responseToBrowser(resp, info);
	}
}
