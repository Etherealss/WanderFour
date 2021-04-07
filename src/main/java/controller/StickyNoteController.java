package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
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
import pojo.po.StickyNote;
import service.StickyNoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/20
 */
@Controller
public class StickyNoteController {

	private final Logger logger = Logger.getLogger(StickyNoteController.class);
	private StickyNoteService stickyNoteService;

	@RequestMapping(value = "/StickyNoteServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("获取便利贴");

		JSONObject params = GetParamChoose.getJsonByUrl(req);
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "获取便利贴");
		if (paramMissing){
			return;
		}

		Integer currentPage = params.getInteger("currentPage");
		JSONObject json = new JSONObject();
		if (currentPage != null){
			// 获取列表
			try {
				List<StickyNote> stickyNoteList = stickyNoteService.getStickyNoteList(currentPage, DaoEnum.NOTES_ROWS);

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

	@RequestMapping(value = "/StickyNoteServlet", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("创建便利贴");
	}

	@RequestMapping(value = "/StickyNoteServlet", method = RequestMethod.DELETE)
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("删除便利贴");
	}
}
