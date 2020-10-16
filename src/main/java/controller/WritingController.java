package controller;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.util.ControllerUtil;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取作品");
		//获取参数
		JSONObject params = ControllerUtil.getJsonByUrl(req);
		if (params == null) {
			//空参为异常，需要有参数才能获取指定数据
			ControllerUtil.respNoParameterEeeor(resp, "查询文章");
			return;
		}
		logger.trace("params = " + params);

		//确认作品类型，如果是文章，url为?article=xxx，可以获取article参数
		boolean isArticle = (params.get("article") != null);
		//根据类型获取实体和Service
		if (isArticle) {
			logger.trace("Article get:" + params.get("article"));
			WritingService<Article> artivleService = ServiceFactory.getArticleService();
			Article article = artivleService.getWriting(Long.valueOf(String.valueOf(params.get("article"))));

			JSONObject jsonObject = new JSONObject();
			ResultState state;

			if (article == null) {
				// 查不到文章 跳转到404
				state = new ResultState(ResultType.ERROR, "参数错误，查询不到文章");
				jsonObject.put("state", state);
				throw new ServletException("参数错误，查询不到文章，跳转");
			} else {
				//查询到，传给前端
				jsonObject.put("article", article);
				state = new ResultState(ResultType.SUCCESS, "查询结果");
			}

			jsonObject.put("state", state);
			logger.trace(jsonObject);
			ControllerUtil.respToBrowser(resp, jsonObject);

		} else {
			if (params.get("posts") == null) {
				ControllerUtil.respNoParameterEeeor(resp, "发表作品");
			}
			logger.trace("Posts get:" + params.get("posts"));
			WritingService<Posts> postsService = ServiceFactory.getPostsService();
			//TODO 帖子GET
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("发布作品");
		String type = req.getParameter("type");
		// 空参检查
		if ("".equals(type) || type == null) {
			ControllerUtil.respNoParameterEeeor(resp, "发表作品");
		}
		//确定类型
		boolean isArticle = ("article".equals(type));
		//根据类型获取实体和Service
		if (isArticle) {
			Article article = ControllerUtil.getForm(req, Article.class);
			logger.debug(article);

			if (article == null) {
				ControllerUtil.respNoParameterEeeor(resp, "发表文章");
				return;
			}

			WritingService<Article> service = ServiceFactory.getArticleService();
			//发表新文章
			ResultType state = service.publishNewWriting(article);

			ControllerUtil.respOnlyStateToBrowser(resp, state, "发表结果");
		} else {
			logger.trace("Posts post:" + type);
			WritingService<Posts> postsService = ServiceFactory.getPostsService();
			//TODO 帖子POST
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = ControllerUtil.getJson(req);

		if (json == null) {
			ControllerUtil.respNoParameterEeeor(resp, "修改作品(参数)");
		}

		String type = json.getString("type");
		logger.trace("修改作品 type = " + type);
		logger.trace(json);
		boolean isArticle = ("article".equals(type));

		//空参检查
		if ("".equals(type) || type == null) {
			ControllerUtil.respNoParameterEeeor(resp, "修改作品(type参数)");
		}

		//根据类型获取实体和Service
		if (isArticle) {
			Article article = ControllerUtil.getForm(req, Article.class);
			logger.debug(article);

			//空参检查
			if (article == null) {
				ControllerUtil.respNoParameterEeeor(resp, "修改文章");
			}

			WritingService<Article> service = ServiceFactory.getArticleService();
			//发表新文章
			ResultType state = service.updateWriting(article);

			ControllerUtil.respOnlyStateToBrowser(resp, state, "修改结果");
		} else {
			logger.trace("Posts put:" + type);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("删除作品");
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
//				ControllerUtil.setWritingWithData(req, artile);
				articleAction(info, action, artivleService, artile);
				break;
			case "postposts":
			case "addposts":
				Posts posts = new Posts();
//				ControllerUtil.setWritingWithData(req, posts);
				postsAction(info, action, postsService, posts);
				break;
			case "deletearticle":
				assert artivleService != null;
				articleDelete(info, artivleService, Long.valueOf(req.getParameter("writingId")), req.getParameter("deleterId"));
				break;
			case "deleteposts":
				postsDelete(info, postsService, Long.valueOf(req.getParameter("writingId")), req.getParameter("deleterId"));
				break;
			default:
				logger.error("错误的CRUD操作");
		}
		//返回客户端
//		ControllerUtil.responseToBrowser(resp, info);
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
		ResultType state;
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
			ResultType state;
			if (writing != null) {
				state = ResultType.SUCCESS;
			} else {
				state = ResultType.EXCEPTION;
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
		ResultType state = service.deleteWriting(writingId, deleterId);
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
