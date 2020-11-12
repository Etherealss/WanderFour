package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import pojo.bean.PageBean;
import pojo.dto.ResultState;
import pojo.po.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 用户信息相关
 * @date 2020/10/30
 */
@WebServlet("/UserInfoServlet")
public class UserInfoController extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取用户信息");
		Long userId = ControllerUtil.getUserId(req);

		JSONObject resJson = new JSONObject();
		ResultState state;

		if (userId == null) {
			ResultType type = ResultType.NOT_LOGGED;
			state = new ResultState(type, "用户未登录");

		} else {
			UserService userService = ServiceFactory.getUserService();
			try {
				User user = userService.getLoggedUserInfo(userId);
				state = new ResultState(ResultType.LOGGED, "用户已登录，用户信息获取成功");

				if (user == null) {
					state = new ResultState(ResultType.EXCEPTION, "用户已登录，但是获取用户信息失败");
				} else {
					resJson.put("user", user);
					logger.debug("用户已登录，id=" + user.getId() + ", userType = " + user.getUserType());
				}

			} catch (Exception e) {
				e.printStackTrace();
				state = new ResultState(ResultType.EXCEPTION, "异常");
			}
		}
		resJson.put("state", state);
		ResponseChoose.respToBrowser(resp, resJson);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("修改用户信息");
		User user = GetParamChoose.getObjByJson(req, User.class);
		Long userId = ControllerUtil.getUserId(req);
		if (user == null) {
			ResponseChoose.respNoParameterError(resp, "获取不到User对象");
			return;
		} else if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		UserService userService = ServiceFactory.getUserService();
		try {
			boolean b = userService.updateUserInfo(user);
			if (!b) {
				ResponseChoose.respToBrowser(resp, new ResultState(ResultType.EXCEPTION, "修改用户异常"));
				return;
			}
			ResponseChoose.respToBrowser(resp, new ResultState(ResultType.SUCCESS, "修改用户信息成功"));

		} catch (Exception e) {
			e.printStackTrace();
			ResponseChoose.respToBrowser(resp, new ResultState(ResultType.EXCEPTION, "修改用户异常"));
		}
	}
}
