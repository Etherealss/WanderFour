package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import pojo.po.LikeRecord;
import service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 点赞
 * @date 2020/10/13
 */
@WebServlet("/LikeServlet")
public class LikeController extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("点赞");
		LikeRecord record = GetParamChoose.getObjByJson(req, LikeRecord.class);
		//空参检查
		if (record == null) {
			ResponseChoose.respNoParameterError(resp, "点赞");
			return;
		}

		Long userId = ControllerUtil.getUserId(req);
		if (userId == null) {
			logger.error("点赞时用户未登录");
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}
		record.setUserid(userId);
		logger.debug(record.toString());

		//点赞
		LikeService service = ServiceFactory.getLikeService();
		ResultType resultType = null;
		try {
			resultType = service.likeOrUnlike(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		ResponseChoose.respOnlyStateToBrowser(resp, resultType, "点赞操作");

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("doGet");
	}
}
