package controller;

import com.alibaba.druid.util.StringUtils;
import common.util.ControllerUtil;
import pojo.po.User;
import common.enums.ResultState;
import common.factory.ServiceFactory;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author 寒洲
 * @description 登录注册操作
 * @date 2020/10/2
 */
@WebServlet("/UserEnterServlet")
public class UserEnterController extends BaseServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		if ("login".equals(action)){
			login(req, resp);
		} else if ("register".equals(action)){
			register(req, resp);
		} else {
			logger.error("错误的方法: action = " + action);
			throw new ServletException("错误的方法");
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO 策略
		logger.trace("用户登录...");

		String email = req.getParameter("email");
		String password = req.getParameter("pw");

		//执行操作，获取结果
		UserService us = ServiceFactory.getUserService();
		ResultState state = us.validateUserLogin(email, password);

		if (state == ResultState.SUCCESS){
			//密码正确，检查异地登录
			logger.trace("密码正确，检查异地登录");
			HttpSession session = req.getSession();
			String sessionId = session.getId();

			//通过ServletContext检查异地登录
			ServletContext servletContext = session.getServletContext();
			if (servletContext.getAttribute(email) == null){
				//未登录，用email做标识，存入请求的sessionId
				logger.trace("未登录，好了，现在登录了");
				servletContext.setAttribute(email, sessionId);
			} else if(servletContext.getAttribute(email)!= null &&
					!StringUtils.equals(sessionId ,servletContext.getAttribute(email).toString())){
				//如果servletContext中存在email，说明已登录，比较两个sessionId
				//如果两个sessionId相同，说明是同个地点登录
				//输出“您已登录”
				logger.trace("已经登录了");
				state = ResultState.LOGGED;
			} else {
				//如果两个sessionId 不相同，说明是已在别处登录，踢下异地用户
				logger.trace("异地登录！");
				servletContext.removeAttribute(email);
				servletContext.setAttribute(email, sessionId);
			}
		}
		logger.info(state);
		Map<String, Object> info = new Hashtable<>();
		info.put("state", state);
		ControllerUtil.responseToBrowser(resp, info);
	}

	public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("用户注册...");
		//node.js
		//获取请求参数
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");
		String password = req.getParameter("pw");
		String userType = req.getParameter("userType");
		String sex_str = req.getParameter("sex");
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
		logger.debug(user);
		//提交用户信息
		UserService us = new UserServiceImpl();
		Map<String, Object> info = new Hashtable<>();
		info.put("state", us.registerNewUser(user));
		ControllerUtil.responseToBrowser(resp, info);
	}
}
