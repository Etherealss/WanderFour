package controller;

import com.alibaba.fastjson.JSONObject;
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
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 修改密码
 * @date 2020/11/3
 */
@Controller
public class UserPasswordController {
	private final Logger logger = Logger.getLogger(UserPasswordController.class);

	private UserService userService;

	@RequestMapping(value = "/UserPasswordServlet", method = RequestMethod.PUT)
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Long userId = WebUtil.getUserId(req);
		if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		JSONObject params = GetParamChoose.getJsonByJson(req);

		//空参检查
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "修改密码",
				"originalPw", "newPw");
		if (paramMissing) {
			return;
		}

		String originalPw = params.getString("originalPw");
		String newPw = params.getString("newPw");

		ResultState state;
		try {
			ResultType resultType = userService.updateUserPw(userId, originalPw, newPw);
			state = new ResultState(resultType, "修改结果");
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "修改异常");
		}
		ResponseChoose.respToBrowser(resp, state);
	}
}
