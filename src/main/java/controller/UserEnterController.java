package controller;

import common.bean.User;
import common.emun.ResultState;
import common.factory.ServiceFactory;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 寒洲
 * @description 登录注册操作
 * @date 2020/10/2
 */
@WebServlet("/UserEnterServlet")
public class UserEnterController extends BaseUserServlet {

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		logger.trace("用户登录...");

		String email = req.getParameter("email");
		String password = req.getParameter("pw");

		//执行操作，获取结果
		UserService us = ServiceFactory.getUserService();
		ResultState code = us.validateUserLogin(email, password);

		logger.info(code);
		info.put("code", code);
		responseToBrowser(resp);
	}

	public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("用户注册...");
		//获取请求参数
		String nickname = req.getParameter("regi-nickname");
		String email = req.getParameter("regi-email");
		String password = req.getParameter("regi-pw1");
		String userType = req.getParameter("userType");
		String sex_str = req.getParameter("regi-sex");
		//判断性别，小哥哥为true
		Boolean sex = "male".equals(sex_str);
		//根据性别加载默认头像
		// 封装到user对象中
		String avatarPath;
		if (sex) {
			avatarPath = this.getServletContext().getRealPath("boy.png");
		} else {
			avatarPath = this.getServletContext().getRealPath("girl.png");
		}
		logger.debug("avatarPath = " + avatarPath);
		User user = new User(email, password, nickname, sex, avatarPath, userType, new Date());
		//提交用户信息
		UserService us = new UserServiceImpl();
		info.put("state", us.registerNewUser(user));
		responseToBrowser(resp);
	}
}
