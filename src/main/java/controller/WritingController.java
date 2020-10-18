package controller;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author 寒洲
 * @description 作品（文章和帖子）的控制器
 * @date 2020/10/7
 */
@WebServlet("/WritingServlet")
public class WritingController extends BaseServlet {

	private final static String TYPE_ARTICLE = "article";
	private final static String TYPE_POSTS = "posts";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取参数
		JSONObject params = GetParamChoose.getJsonByUrl(req);
		if (params == null) {
			//空参为异常，需要有参数才能获取指定数据
			ResponseChoose.respNoParameterError(resp, "查询作品");
			return;
		}
		logger.trace("获取作品 params = " + params);

		//确认作品类型，如果是文章，url为?article=xxx，可以获取article参数
		boolean isArticle = (params.get(TYPE_ARTICLE) != null);
		//根据类型获取实体和Service
		if (isArticle) {
			logger.trace("Article get:" + params.get(TYPE_ARTICLE));
			WritingService<Article> artivleService = ServiceFactory.getArticleService();
			Article article = artivleService.getWriting(Long.valueOf(String.valueOf(params.get(TYPE_ARTICLE))));

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
			ResponseChoose.respToBrowser(resp, jsonObject);

		} else {
			if (params.get(TYPE_POSTS) == null) {
				ResponseChoose.respNoParameterError(resp, "获取作品");
				return;
			}
			logger.trace("Posts get:" + params.get(TYPE_POSTS));
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
			ResponseChoose.respNoParameterError(resp, "发表作品");
			return;
		}
		//确定类型
		boolean isArticle = (TYPE_ARTICLE.equals(type));
		//根据类型获取实体和Service
		if (isArticle) {
			Article article = GetParamChoose.getObjByForm(req, Article.class);
			logger.debug(article);

			if (article == null) {
				logger.fatal("发表文章空参");
				ResponseChoose.respNoParameterError(resp, "发表文章");
				return;
			}

			WritingService<Article> service = ServiceFactory.getArticleService();
			//发表新文章
			ResultType state = service.publishNewWriting(article);

			ResponseChoose.respOnlyStateToBrowser(resp, state, "发表结果");
		} else {
			logger.trace("Posts post:" + type);
			WritingService<Posts> postsService = ServiceFactory.getPostsService();
			//TODO 帖子POST
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject param = GetParamChoose.getJsonByJson(req);
		logger.trace(param);
		//空参检查
		if (param == null) {
			ResponseChoose.respNoParameterError(resp, "修改作品(参数)");
			return;
		}

		String type = param.getString("type");
		logger.trace("修改作品 type = " + type);

		boolean isArticle = (TYPE_ARTICLE.equals(type));

		//空参检查
		if ("".equals(type) || type == null) {
			ResponseChoose.respNoParameterError(resp, "修改作品(type参数)");
			return;
		}

		//根据类型获取实体和Service
		if (isArticle) {
			Article article = GetParamChoose.getObjByParam(param, Article.class);
			logger.debug("获取article参数：\n\t" + article);

			//空参检查
			if (article == null) {
				ResponseChoose.respNoParameterError(resp, "修改文章");
				return;
			}

			WritingService<Article> service = ServiceFactory.getArticleService();
			article.setUpdateTime(new Date());
			//修改文章
			ResultType state = service.updateWriting(article);

			ResponseChoose.respOnlyStateToBrowser(resp, state, "修改结果");
		} else {
			logger.trace("Posts put:" + type);
			//TODO 帖子PUT
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject param = GetParamChoose.getJsonByJson(req);
		logger.trace("删除作品" + param);
		//空参检查
		if (param == null) {
			ResponseChoose.respNoParameterError(resp, "修改作品(参数)");
			return;
		}

		Long articleId = param.getLong(TYPE_ARTICLE);
		boolean isArticle = articleId != null;
		if (isArticle) {
			//删除文章
			WritingService<Article> service = ServiceFactory.getArticleService();
			ResultType resultType = service.deleteWriting(articleId, param.getLong("deleterId"));
			ResponseChoose.respOnlyStateToBrowser(resp, resultType, "删除结果");
		} else {
			Long postsId = param.getLong(TYPE_POSTS);
			//空参检查
			if (postsId == null) {
				ResponseChoose.respNoParameterError(resp, "参数(type)错误");
				return;
			}
			WritingService<Posts> service = ServiceFactory.getPostsService();
			//Posts delete
		}
	}

}
