package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ApplicationConfig;
import common.enums.AttrEnum;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import common.util.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 用户头像
 * @date 2020/11/20
 */
@Controller
public class UserAvatarController {

	private Logger logger = Logger.getLogger(UserAvatarController.class);

	@RequestMapping(value = "UserAvatarController", method = RequestMethod.PUT)
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("修改头像");
		Long userId = WebUtil.getUserId(req);
		if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}
		JSONObject params = GetParamChoose.getJsonByJson(req);
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "avatar");
		if (paramMissing){
			return;
		}

		String avatarStr = params.getString("avatar");
		// 用png格式储存
		String filePath = ApplicationConfig.AVATAR_PATH + userId + ".png";
		FileUtil.generateImageByBase64(avatarStr, filePath);
	}
}
