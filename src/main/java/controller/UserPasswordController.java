package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import common.util.Md5Utils;
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
 * @description 修改密码
 * @date 2020/11/3
 */
@WebServlet("/UserPasswordServlet")
public class UserPasswordController extends BaseServlet {

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = ControllerUtil.getUserId(req);
		if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		JSONObject params = GetParamChoose.getJsonByJson(req);

		//空参检查
		boolean paramMissing = ControllerUtil.isParamMissing(resp, "修改密码",
				"orginalPw", "newPw");
		if (paramMissing) {
			return;
		}

		String orginalPw = params.getString("orginalPw");
		String newPw = params.getString("newPw");

		UserService service = ServiceFactory.getUserService();
		ResultState state;
		try {
			ResultType resultType = service.updateUserPw(userId, orginalPw, newPw);
			state = new ResultState(resultType, "修改结果");
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "修改异常");
		}
		ResponseChoose.respToBrowser(resp, state);
	}
}
