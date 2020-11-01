package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
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
 * @description 获取已登录的用户的信息
 * @date 2020/10/30
 */
@WebServlet("/UserLoginServlet")
public class UserController extends BaseServlet {

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
}
