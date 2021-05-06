package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.EsEnum;
import common.enums.ResultType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.bo.EsBo;
import pojo.bo.PageBo;
import pojo.dto.ResultState;
import service.EsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author 寒洲
 * @description 搜索
 * @date 2020/10/31
 */
@Controller
public class WritingSearchController {
	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
	private EsService esService;

	@RequestMapping(value = "/WritingSearchServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("搜索文章");
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		//encodeURIComponent解码
		WebUtil.isParamMissing(resp, params, "搜索", "currentPage");

		String wd = params.getString("wd");
		if (wd != null && !"".equals(wd)) {
			JSONObject data = this.doSearch(params);
			ResponseChoose.respToBrowser(resp, data);
		}
	}


	/**
	 * 搜索
	 * @param params 参数
	 */
	private JSONObject doSearch(JSONObject params) {
		JSONObject json = new JSONObject();
		ResultState state;
		PageBo<EsBo> pageData;
		try {

			String searchWord = URLDecoder.decode(params.getString("wd"), "UTF-8");
			logger.debug("用户搜索：" + searchWord);

			pageData = esService.searchByHighLigh(searchWord, 0, EsEnum.ES_SEARCH_SIZE);

			state = new ResultState(ResultType.SUCCESS, "搜索成功");
			json.put("pageDate", pageData);

		} catch (UnsupportedEncodingException e) {
			logger.error("URIComponent 解码失败");
			state = new ResultState(ResultType.ERROR, "URIComponent 解码失败");
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "出现异常");
		}

		json.put("state", state);
		return json;
	}

	@Autowired
	public void setEsService(EsService esService) {
		this.esService = esService;
	}
}
