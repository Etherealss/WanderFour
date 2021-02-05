package controller.websocket;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import controller.BaseServlet;
import pojo.dto.ResultState;
import pojo.po.User;
import service.FriendRelationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description 好友
 * @date 2020/11/20
 */
@WebServlet("/socket/friend")
public class FriendController extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取用户好友列表");
		Long userId = ControllerUtil.getUserId(req);
		if (userId == null){
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		FriendRelationService service = ServiceFactory.getFriendRelationService();
		JSONObject json = new JSONObject();
		try {
			List<User> friendsInfo = service.getFriendsInfo(userId);
			json.put("friends", friendsInfo);
			json.put("state", new ResultState(ResultType.SUCCESS, "获取好友列表成功"));
		} catch (Exception e) {
			e.printStackTrace();
			json.put("state", new ResultState(ResultType.EXCEPTION, "获取好友列表异常"));
		}
		logger.debug(json);
		ResponseChoose.respToBrowser(resp, json);
	}
}
