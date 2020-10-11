package controller;

import common.dto.ResultState;
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
 * @description 文章和帖子的控制器
 * @date 2020/10/7
 */
@WebServlet("/ArtilePostServlet")
public class ArtilePostsController extends BaseServlet {

	/**
	 * 文章或帖子的增删改查操作
	 * @param info 预设的返回信息包
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void writingCRUD(Map<String, Object> info, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//确认作品类型
		String writingType = req.getParameter("type");
		boolean isArticle = writingType.equals("article");
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
		if (action.equals("get") || action.equals("delete")) {
			if (isArticle) {
				articleAction(info, action, artivleService, Long.valueOf(req.getParameter("article")));
			} else {
				postsAction(info, action, postsService, Long.valueOf(req.getParameter("posts")));
			}
		} else {
			assert action.equals("add") || action.equals("post");
			if (isArticle) {
				Article artile = new Article();
				ControllerUtil.setWritingWithData(req, artile);
				articleAction(info, action, artivleService, artile);
			} else {
				Posts posts = new Posts();
				ControllerUtil.setWritingWithData(req, posts);
				postsAction(info, action, postsService, posts);
			}

		}
		//返回客户端
		responseToBrowser(resp, info);
	}

	/**
	 * 添加、修改
	 * @param info
	 * @param action
	 * @param service
	 * @param article
	 */
	private void articleAction(Map<String, Object> info, String action, WritingService<Article> service, Article article) {
		if (action.equals("add")) {
			ResultState state = service.publishNewWriting(article);
			info.put("state", state);
		}else{
			assert action.equals("post");
		}

		//因为Map对象没有变动，所以不必返回
	}

	/**
	 * 获取、删除
	 * @param info
	 * @param action
	 * @param service
	 * @param id
	 */
	private void articleAction(Map<String, Object> info, String action, WritingService<Article> service, Long id) {
		if (action.equals("get")) {
			Article writing = service.getWriting(id);
			info.put("article", writing);
		} else {
			assert action.equals("delete");


		}
	}

	/**
	 * 添加、修改
	 * @param info
	 * @param action
	 * @param service
	 * @param posts
	 */
	private void postsAction(Map<String, Object> info, String action, WritingService<Posts> service, Posts posts) {

	}

	/**
	 * 获取、删除
	 * @param info
	 * @param action
	 * @param service
	 * @param id
	 */
	private void postsAction(Map<String, Object> info, String action, WritingService<Posts> service, Long id) {

	}
}
