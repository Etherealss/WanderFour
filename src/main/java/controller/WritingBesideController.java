package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.ResponseChoose;
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
 * @description 在显示文章/问贴时，获取侧边的推荐作品
 * @date 2020/10/29
 */
@WebServlet("/WritingBesideServlet")
public class WritingBesideController extends BaseServlet{

	private final static String TYPE_ARTICLE = TargetType.ARTICLE.val();
	private final static String TYPE_POSTS = TargetType.POSTS.val();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("在显示文章/问贴时，获取侧边的推荐作品");
		String type = req.getParameter("type");
		String partition = req.getParameter("partition");

		//TODO 工具类实现判空
		if ("".equals(type) || type == null){
			logger.error("type参数缺失");
			ResponseChoose.respNoParameterError(resp, "type参数缺失");
			return;
		}
		if ("".equals(partition) || partition == null) {
			logger.error("partition参数缺失");
			ResponseChoose.respNoParameterError(resp, "partition参数缺失");
			return;
		}
		int part = Integer.parseInt(partition);


		JSONObject resJson = new JSONObject();
		if (TYPE_ARTICLE.equals(type)){
			//获取文章
			WritingService<Article> service = ServiceFactory.getArticleService();
			try {
				//只获取文章的标题和id，用Simple方法
				List<Article> timeList = service.getSimpleWritingList(part, DaoEnum.ORDER_BY_TIME);
				List<Article> likeList = service.getSimpleWritingList(part, DaoEnum.ORDER_BY_LIKE);
				resJson.put("new", timeList);
				resJson.put("hot", likeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (TYPE_POSTS.equals(type)){
			//获取文章
			WritingService<Posts> service = ServiceFactory.getPostsService();
			try {
				List<Posts> timeList = service.getSimpleWritingList(part, DaoEnum.ORDER_BY_TIME);
				List<Posts> likeList = service.getSimpleWritingList(part, DaoEnum.ORDER_BY_LIKE);
				resJson.put("new", timeList);
				resJson.put("hot", likeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.error("获取侧栏推荐作品，type参数错误");
			ResponseChoose.respWrongParameterError(resp, "type参数错误");
			return;
		}

		ResponseChoose.respToBrowser(resp, resJson);
	}
}
