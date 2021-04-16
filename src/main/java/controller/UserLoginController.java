package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.ResultState;
import pojo.po.User;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 寒洲
 * @description 获取已登录的用户的信息
 * @date 2020/10/30
 */
@Controller
public class UserLoginController {

	private Logger logger = Logger.getLogger(UserLoginController.class);
	private UserService userService;

	@RequestMapping(value = "/UserLoginServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		logger.trace("获取用户信息");
		Long userId = WebUtil.getUserId(req);
		JSONObject resJson = new JSONObject();
		ResultState state;
		if (userId == null) {
			ResultType type = ResultType.NOT_LOGGED;
			state = new ResultState(type, "用户未登录");
		} else {
			try {
				User user = userService.getUserInfo(userId);
				state = new ResultState(ResultType.LOGGED, "用户已登录，用户信息获取成功");

				if (user == null) {
					state = new ResultState(ResultType.EXCEPTION, "用户已登录，但是获取用户信息失败");
				} else {
					resJson.put("user", user);
				}

			} catch (Exception e) {
				e.printStackTrace();
				state = new ResultState(ResultType.EXCEPTION, "异常");
			}
		}
		resJson.put("state", state);
		ResponseChoose.respToBrowser(resp, resJson);
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}