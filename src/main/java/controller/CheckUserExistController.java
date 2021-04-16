package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ApplicationConfig;
import common.strategy.choose.GetParamChoose;
import common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.ResultState;
import common.enums.ResultType;
import common.strategy.choose.ResponseChoose;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author 寒洲
 * @description 检查账号是否存在
 * @date 2020/10/3
 */
@Controller
public class CheckUserExistController {

	private final Logger logger = Logger.getLogger(CheckUserExistController.class);

	private UserService userService;


	@RequestMapping(value = "/CheckUserExistServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject params = GetParamChoose.getJsonByUrl(req);

		//空参检查
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "检查账号是否已注册",
				"email");
		if (paramMissing){
			return;
		}

		String email = params.getString("email");

		// encodeURLComponent转码
		email = URLDecoder.decode(email, ApplicationConfig.CODING_FORMAT);

		//获取service，检查email是否存在
		ResultType state = null;
		try {
			state = userService.checkUserExist(email);
		} catch (Exception e) {
			logger.error("检查账号是否已注册异常", e);

		}
		logger.debug("email = " + email + ", state = " + state);
		//因为接口规范所以需要再次包装json对象，而不是直接发送state.getJson()
		JSONObject jsonObject = new JSONObject();
		ResultState result = new ResultState(state, "账号查询结果");
		jsonObject.put("state", result);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
