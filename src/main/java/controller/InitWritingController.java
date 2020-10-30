package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.enums.Partition;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
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
		String order = req.getParameter("order");
		String type = req.getParameter("type");

		ControllerUtil.checkParamExist(resp, "初始化分区页面", partStr, order, type);


		int partition = Integer.parseInt(partStr);
		JSONObject resultJson = new JSONObject();

		try {
			if (TargetType.ARTICLE.val().equals(type)) {
				WritingService<Article> articleService = ServiceFactory.getArticleService();
				List<WritingBean<Article>> articleList = articleService.getWritingList(partition, order);
				resultJson.put("writings", articleList);

			} else if (TargetType.POSTS.val().equals(type)) {
				WritingService<Posts> postsService = ServiceFactory.getPostsService();
				List<WritingBean<Posts>> postsList = postsService.getWritingList(partition, order);
				resultJson.put("writings", postsList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseChoose.respToBrowser(resp, resultJson);
	}
}
