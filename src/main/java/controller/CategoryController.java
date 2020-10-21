package controller;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
import service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 分类
 * @date 2020/10/20
 */
@WebServlet("/CategoryServlet")
public class CategoryController extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("分类Get");
		String partition = req.getParameter("partition");

		//空参判断
		if (partition == null){
			ResponseChoose.respNoParameterError(resp, "获取分类信息(partition参数)");
			return;
		}
		logger.debug(partition);

		CategoryService service = ServiceFactory.getCategoryService();
		//获取分区json
		JSONObject categoryJson = null;
		//传给浏览器的信息包
		JSONObject result = new JSONObject();
		try {
			categoryJson = service.getAllCategoryByPart(Integer.parseInt(partition));
			//获取成功
			ResultState state = new ResultState(ResultType.SUCCESS, "获取分类结果");
			result.put("state", state);
			result.put("category", categoryJson);
		} catch (Exception e) {
			e.printStackTrace();
			//异常
			result.put("state",  new ResultState(ResultType.EXCEPTION, "获取分类结果"));
		}

		ResponseChoose.respToBrowser(resp, result);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("分类Post");
	}
}
