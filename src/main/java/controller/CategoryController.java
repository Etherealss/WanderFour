package controller;

import com.alibaba.fastjson.JSONObject;
import common.strategy.choose.GetParamChoose;
import common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.ResultState;
import common.enums.ResultType;
import common.strategy.choose.ResponseChoose;
import service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 分类
 * @date 2020/10/20
 */
@Controller
public class CategoryController {

	private final Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
	private CategoryService categoryService;

	@RequestMapping(value = "CategoryServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("分类Get");
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		//空参判断
		boolean paramMissing = WebUtil.isParamMissing(resp, params, "获取分类", "partition");
		if (paramMissing){
			return;
		}

		Integer partition = params.getInteger("partition");
		logger.debug(String.valueOf(partition));

		//获取分区json
		JSONObject categoryJson;
		//传给浏览器的信息包
		JSONObject result = new JSONObject();
		try {
			categoryJson = categoryService.getAllCategoryByPart(partition);
			//获取成功
			ResultState state = new ResultState(ResultType.SUCCESS, "获取分类结果");
			result.put("state", state);
			result.put("category", categoryJson);
		} catch (Exception e) {
			logger.error("获取分类异常", e);
			//异常
			result.put("state",  new ResultState(ResultType.EXCEPTION, "获取分类结果"));
		}

		ResponseChoose.respToBrowser(resp, result);
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
