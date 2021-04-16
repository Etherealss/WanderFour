package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.ApplicationConfig;
import common.enums.AttrEnum;
import common.enums.EsEnum;
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
import service.EsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author 寒洲
 * @description 搜索提示
 * @date 2020/11/17
 */
@Controller
public class WritingSearchTipController {

	private Logger logger = Logger.getLogger(WritingSearchTipController.class);

	private EsService esService;

	@RequestMapping(value = "/WritingSearchTipServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("获取搜索提示词");
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		//encodeURIComponent解码
		WebUtil.isParamMissing(resp, params, "搜索");

		String wd = params.getString("wd");
		if (wd != null && !"".equals(wd)) {
			JSONObject data = this.searchTitle(params);
			ResponseChoose.respToBrowser(resp, data);
		}
	}

	private JSONObject searchTitle(JSONObject params){
		JSONObject json = new JSONObject();
		ResultState state;
		try {

			String searchWord = URLDecoder.decode(params.getString("wd"),
					ApplicationConfig.CODING_FORMAT);

			logger.debug("搜索词：" + searchWord);
			List<String> strings = esService.querySuggestion(searchWord, EsEnum.INDEX_NAME_WRITING, EsEnum.ES_SEARCH_SIZE);

			state = new ResultState(ResultType.SUCCESS, "已获取搜索提示");
			json.put("tips", strings);

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
