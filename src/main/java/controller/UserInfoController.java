package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.enums.ResultType;
import common.strategy.choose.GetParamChoose;
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
@Controller
public class UserInfoController {
	private final Logger logger = Logger.getLogger(UserInfoController.class);
	private UserService userService;

	@RequestMapping(value = "/UserInfoServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("用户获取其他用户的信息");
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "获取其他用户的信息", "userid");
		String userIdStr = params.get("userid").toString();
		if (paramMissing) {
			return;
		} else if (AttrEnum.UNDEFINED.equals(userIdStr)) {
			ResponseChoose.respWrongParameterError(resp, "参数undefined");
			return;
		}

		JSONObject resJson = new JSONObject();
		ResultState state;

		try {
			// 获取请求的用户信息
			Long userid = Long.valueOf(userIdStr);
			User user = userService.getUserInfo(userid);

			if (user == null) {
				state = new ResultState(ResultType.EXCEPTION, "获取用户信息失败");
			} else {
				resJson.put("user", user);
				state = new ResultState(ResultType.SUCCESS, "获取用户信息成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "异常");
		}
		resJson.put("state", state);
		ResponseChoose.respToBrowser(resp, resJson);
	}

	@RequestMapping(value = "/UserInfoServlet", method = RequestMethod.PUT)
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("修改用户信息");
		User user = GetParamChoose.getObjByJson(req, User.class);
		Long userId = WebUtil.getUserId(req);
		if (user == null) {
			ResponseChoose.respNoParameterError(resp, "获取不到User对象");
			return;
		} else if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		try {
			userService.updateUserInfo(user);
			ResponseChoose.respToBrowser(resp, new ResultState(ResultType.SUCCESS, "修改用户信息成功"));

		} catch (Exception e) {
			e.printStackTrace();
			ResponseChoose.respToBrowser(resp, new ResultState(ResultType.EXCEPTION, "修改用户异常"));
		}
	}
}
