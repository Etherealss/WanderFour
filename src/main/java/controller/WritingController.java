package controller;

import common.enums.ResultState;
import common.factory.ServiceFactory;
import common.util.ControllerUtil;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 寒洲
 * @description 作品（文章和帖子）的控制器
 * @date 2020/10/7
 */
@WebServlet("/WritingServlet")
public class WritingController extends BaseServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	/**
	 * 文章或帖子的增删改查操作
	 * @param info 预设的返回信息包
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void writingCRUD(Map<String, Object> info, HttpServletRequest req,
	                        HttpServletResponse resp) throws ServletException, IOException {
		//确认作品类型
		String writingType = req.getParameter("type");
		boolean isArticle = "article".equals(writingType);
		WritingService<Article> artivleService = null;
		WritingService<Posts> postsService = null;
		//根据类型获取实体和Service
		if (isArticle) {
			artivleService = ServiceFactory.getArticleService();
		} else {
			postsService = ServiceFactory.getPostsService();
		}
		//确认操作类型
		String action = req.getParameter("action");
		String judge = action + "" + writingType;
		switch (judge) {
			case "getarticle":
				articleGet(info, action, artivleService, Long.valueOf(req.getParameter("article")));
				break;
			case "getposts":
				postsGet(info, action, postsService, Long.valueOf(req.getParameter("posts")));
				break;
			case "postarticle":
			case "addarticle":
				Article artile = new Article();
				ControllerUtil.setWritingWithData(req, artile);
				articleAction(info, action, artivleService, artile);
				break;
			case "postposts":
			case "addposts":
				Posts posts = new Posts();
				ControllerUtil.setWritingWithData(req, posts);
				postsAction(info, action, postsService, posts);
				break;
			case "deletearticle":
				assert artivleService != null;
				articleDelete(info, artivleService, Long.valueOf(req.getParameter("writingId")), req.getParameter("deleterId"));
				break;
			case "deleteposts":
				postsDelete(info, postsService, Long.valueOf(req.getParameter("writingId")), req.getParameter("deleterId"));
				break;
				//TODO default
		}
		//返回客户端

		ControllerUtil.responseToBrowser(resp, info);
	}

	/**
	 * 添加、修改
	 * @param info
	 * @param action
	 * @param service
	 * @param article
	 */
	private void articleAction(Map<String, Object> info, String action,
	                           WritingService<Article> service, Article article) {
		ResultState state;
		if ("add".equals(action)) {
			state = service.publishNewWriting(article);
		} else {
			assert "post".equals(action);
			state = service.updateWriting(article);
		}
		info.put("state", state);
		//因为Map对象没有变动，所以不必返回
	}

	/**
	 * 获取
	 * @param info
	 * @param action
	 * @param service
	 * @param id      作品id
	 */
	private void articleGet(Map<String, Object> info, String action,
	                        WritingService<Article> service, Long id) {
		if ("get".equals(action)) {
			Article writing = service.getWriting(id);
			ResultState state;
			if (writing != null) {
				state = ResultState.SUCCESS;
			} else {
				state = ResultState.EXCEPTION;
			}
			info.put("article", writing);
			info.put("state", state);
		}
	}

	/**
	 * 删除
	 * @param info
	 * @param service
	 * @param writingId
	 * @param deleterId
	 */
	private void articleDelete(Map<String, Object> info, WritingService<Article> service,
	                           Long writingId, String deleterId) {
		ResultState state = service.deleteWriting(writingId, deleterId);
		info.put("state", state);
	}

	/**
	 * 添加、修改
	 * @param info
	 * @param action
	 * @param service
	 * @param posts
	 */
	private void postsAction(Map<String, Object> info, String action,
	                         WritingService<Posts> service, Posts posts) {

	}

	/**
	 * 获取
	 * @param info
	 * @param action
	 * @param service
	 * @param id
	 */
	private void postsGet(Map<String, Object> info, String action,
	                      WritingService<Posts> service, Long id) {

	}

	/**
	 * 删除
	 * @param info
	 * @param service
	 * @param writingId
	 * @param deleterId
	 */
	private void postsDelete(Map<String, Object> info, WritingService<Posts> service,
	                         Long writingId, String deleterId) {

	}
}
