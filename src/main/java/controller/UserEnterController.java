package controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import pojo.dto.ResultState;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import pojo.po.User;
import common.enums.ResultType;
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

/**
 * @author 寒洲
 * @description 登录注册操作
 * @date 2020/10/2
 */
@WebServlet("/UserEnterServlet")
public class UserEnterController extends BaseServlet {

	private final static String ACTION_LOGIN = "login";
	private final static String ACTION_REGISTER = "register";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		if (ACTION_LOGIN.equals(action)) {
			login(req, resp);
		} else if (ACTION_REGISTER.equals(action)) {
			register(req, resp);
		} else {
			logger.error("错误的方法: action = " + action);
			ResponseChoose.respOnlyStateToBrowser(resp, ResultType.EXCEPTION, "错误的方法，检查method参数：method=login(/register)");
			throw new ServletException("错误的方法action");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("DoGet!");
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO 策略
		logger.trace("用户登录...");

		String email = req.getParameter("email");
		String password = req.getParameter("pw");

		//执行操作，获取结果
		UserService us = ServiceFactory.getUserService();
		ResultType state = null;
		try {
			state = us.validateUserLogin(email, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (state == ResultType.SUCCESS) {
			//密码正确，检查异地登录
			logger.trace("密码正确，检查异地登录");
			HttpSession session = req.getSession();
			String sessionId = session.getId();

			//通过ServletContext检查异地登录
			ServletContext servletContext = session.getServletContext();
			if (servletContext.getAttribute(email) == null) {
				//未登录，用email做标识，存入请求的sessionId
				logger.trace("未登录，好了，现在登录了");
				servletContext.setAttribute(email, sessionId);
			} else if (servletContext.getAttribute(email) != null &&
					!StringUtils.equals(sessionId, servletContext.getAttribute(email).toString())) {
				//如果servletContext中存在email，说明已登录，比较两个sessionId
				//如果两个sessionId相同，说明是同个地点登录
				//输出“您已登录”
				logger.trace("已经登录了");
				state = ResultType.LOGGED;
			} else {
				//如果两个sessionId 不相同，说明是已在别处登录，踢下异地用户
				logger.trace("异地登录！");
				servletContext.removeAttribute(email);
				servletContext.setAttribute(email, sessionId);
			}
		}
		logger.info(state);
		JSONObject jsonObject = new JSONObject();
		ResultState info = new ResultState(state, "登录结果");
		jsonObject.put("state", info);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("用户注册...");
		//获取请求参数，封装到实体中
		User user = GetParamChoose.getObjByForm(req, User.class);

		if (user == null) {
			ResponseChoose.respNoParameterError(resp, "注册");
			return;
		}
		// 封装到user对象中
		String avatarPath;
		//根据性别加载默认头像
		if (user.getSex()) {
			avatarPath = this.getServletContext().getRealPath("boy.png");
		} else {
			avatarPath = this.getServletContext().getRealPath("girl.png");
		}
		logger.debug("avatarPath = " + avatarPath);
		user.setAvatarPath(avatarPath);
		logger.debug(user);
		//提交用户信息，获取业务结果
		Long userid = null;
		try {
			userid = new UserServiceImpl().registerNewUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//注册结果返回浏览器
		respRegisterResult(resp, userid);

	}

	/**
	 * 注册结果返回浏览器
	 * @param resp
	 * @param userid
	 */
	private void respRegisterResult(HttpServletResponse resp, Long userid) {
		logger.debug(userid);
		ResultType resultType = userid == null ? ResultType.EXCEPTION : ResultType.SUCCESS;
		ResultState state = new ResultState(resultType, "注册结果，包含userid");
		JSONObject json = new JSONObject();
		json.put("state", state);
		json.put("userid", userid);
		ResponseChoose.respToBrowser(resp, json);
	}
}
