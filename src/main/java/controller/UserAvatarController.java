package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import common.util.FileUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 用户头像
 * @date 2020/11/20
 */
public class UserAvatarController extends BaseServlet {

	private Logger logger = Logger.getLogger(UserAvatarController.class);

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("修改头像");
		Long userId = ControllerUtil.getUserId(req);
		if (userId == null) {
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}
		JSONObject params = GetParamChoose.getJsonByJson(req);
		boolean paramMissing = ControllerUtil.isParamMissing(resp, params, "avatar");
		if (paramMissing){
			return;
		}

		String avatarStr = params.getString("avatar");
		// 用png格式储存
		String filePath = AttrEnum.AVATAR_PATH + userId + ".png";
		FileUtil.generateImageByBase64(avatarStr, filePath);
	}
}
