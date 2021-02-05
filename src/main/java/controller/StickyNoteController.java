package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import org.apache.log4j.Logger;
import pojo.dto.ResultState;
import pojo.po.StickyNote;
import service.StickyNoteService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/20
 */
public class StickyNoteController extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取便利贴");

		JSONObject params = GetParamChoose.getJsonByUrl(req);
		boolean paramMissing = ControllerUtil.isParamMissing(resp, params, "获取便利贴");
		if (paramMissing){
			return;
		}

		StickyNoteService service = ServiceFactory.getStickyNoteService();
		Integer currentPage = params.getInteger("currentPage");
		JSONObject json = new JSONObject();
		if (currentPage != null){
			// 获取列表
			try {
				List<StickyNote> stickyNoteList = service.getStickyNoteList(currentPage, DaoEnum.NOTES_ROWS);

				// 包装返回信息
				json.put("list", stickyNoteList);
				json.put("state", new ResultState(ResultType.SUCCESS, "获取便利贴成功"));
			} catch (Exception e) {
				e.printStackTrace();
				json.put("state", new ResultState(ResultType.EXCEPTION, "获取便利贴出现异常"));
			}
		}

		ResponseChoose.respToBrowser(resp, json);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("创建便利贴");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("删除便利贴");
	}
}
