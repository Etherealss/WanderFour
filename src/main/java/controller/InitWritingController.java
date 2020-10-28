package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.enums.Partition;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
import pojo.dto.WritingBean;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
@WebServlet("/InitWritingController")
public class InitWritingController extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String partStr = req.getParameter("partition");
		if ("".equals(partStr) || partStr == null){
			ResponseChoose.respNoParameterError(resp, "初始化分区页面，partition参数缺失");
			return;
		}
		int partition = Integer.parseInt(partStr);
		String order = req.getParameter("order");

		JSONObject resultJson = new JSONObject();

		try {
			WritingService<Article> articleService = ServiceFactory.getArticleService();
			WritingService<Posts> postsService = ServiceFactory.getPostsService();
			List<WritingBean> articleList = articleService.getWritingList(partition, order);
			List<WritingBean> postsList = postsService.getWritingList(partition, order);
			resultJson.put("articleList", articleList);
			resultJson.put("postsList", postsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseChoose.respToBrowser(resp, resultJson);
	}
}
