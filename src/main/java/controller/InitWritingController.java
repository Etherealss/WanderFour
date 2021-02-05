package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import pojo.dto.WritingDto;
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
		JSONObject params = GetParamChoose.getJsonByUrl(req);

		//空参检查
		boolean paramMissing = ControllerUtil.isParamMissing(resp, params, "初始化作品",
				"partition", "order", "type");
		if (paramMissing) {
			return;
		}

		String partStr = params.getString("partition");
		String order = params.getString("order");
		String type = params.getString("type");

		//未登录则获取为null，不影响
		Long userId = ControllerUtil.getUserId(req);

		int partition = Integer.parseInt(partStr);
		JSONObject resultJson = new JSONObject();

		try {
			if (TargetType.ARTICLE.val().equals(type)) {
				WritingService<Article> articleService = ServiceFactory.getArticleService();
				List<WritingDto<Article>> articleList = articleService.getWritingList(userId, partition, order);
				resultJson.put("writings", articleList);

			} else if (TargetType.POSTS.val().equals(type)) {
				WritingService<Posts> postsService = ServiceFactory.getPostsService();
				List<WritingDto<Posts>> postsList = postsService.getWritingList(userId, partition, order);
				resultJson.put("writings", postsList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseChoose.respToBrowser(resp, resultJson);
	}
}
