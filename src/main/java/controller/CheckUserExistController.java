package controller;

import com.alibaba.fastjson.JSONObject;
import common.strategy.choose.GetParamChoose;
import common.util.ControllerUtil;
import pojo.dto.ResultState;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
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
public class CheckUserExistController extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject params = GetParamChoose.getJsonByJson(req);

		//空参检查
		boolean paramMissing = ControllerUtil.isParamMissing(resp, "检查账号是否已注册",
				"email");
		if (paramMissing){
			return;
		}

		String email = params.getString("email");
		logger.debug(email);
		//获取service，检查email是否存在
		UserService us = ServiceFactory.getUserService();
		ResultType state = null;
		try {
			state = us.checkUserExist(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("email = " + email + ", state = " + state);
		//因为接口规范所以需要再次包装json对象，而不是直接发送state.getJson()
		JSONObject jsonObject = new JSONObject();
		ResultState result = new ResultState(state, "账号查询结果");
		jsonObject.put("state", result);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}
}
