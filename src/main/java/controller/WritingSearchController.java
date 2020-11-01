package controller;

import com.alibaba.fastjson.JSONObject;
import common.strategy.choose.GetParamChoose;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 搜索
 * @date 2020/10/31
 */
public class WritingSearchController extends BaseServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("搜索文章");
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		
	}
}
