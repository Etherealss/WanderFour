package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.ResultType;
import common.enums.TargetType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import common.util.SecurityUtil;
import common.util.SensitiveUtil;
import filter.SensitiveFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.vo.CommentVo;
import pojo.bo.PageBo;
import pojo.dto.CommentDto;
import pojo.dto.ResultState;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章/帖子的评论回复
 * @date 2020/10/23
 */
@Controller
public class WritingCommentController {

	private final Logger logger = Logger.getLogger(WritingCommentController.class);

	private final static String TYPE_ARTICLE = TargetType.ARTICLE.val();
	private final static String TYPE_POSTS = TargetType.POSTS.val();

	private CommentService commentService;

	@RequestMapping(value = "/WritingCommentServlet", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		logger.trace("获取评论");
		JSONObject param = GetParamChoose.getJsonByUrl(req);

		//空参检查
		boolean paramMissing = WebUtil.isParamMissing(resp, param,"评论");
		if (paramMissing) {
			return;
		}

		//判断文章还是帖子
		String type = param.getString("type");
		//获取请求的数据
		if (TYPE_ARTICLE.equals(type)) {
			logger.trace("获取文章的评论");
			commentService.setTableName(Article.class);
		} else if (TYPE_POSTS.equals(type)) {
			logger.trace("获取问贴的评论");
			commentService.setTableName(Posts.class);
		} else {
			logger.error("评论type参数错误：" + type);
			ResponseChoose.respWrongParameterError(resp, "评论type参数错误");
			return;
		}

		JSONObject jsonObject = new JSONObject();
		ResultState state = null;
		try {
			String orderBy = param.getString("order");
			Long targetId = param.getLong("targetId");
			Long parentId = param.getLong("parentId");
			int currentPage = param.getIntValue("currentPage");


			CommentVo vo = new CommentVo();
			vo.setParentId(parentId);
			//如果targetId没有参数，设置为null，不影响
			vo.setTargetId(targetId);

			//userid可以为null，即未登录
			Long userid = WebUtil.getUserId(req);
			vo.setUserid(userid);

			if (orderBy == null) {
				/*
				没有该参数，说明是获取作品的推荐评论
				 */
				logger.trace("获取推荐评论");
				PageBo<CommentDto> resultList = commentService.getHotCommentList(parentId, userid);
				assert resultList != null;
				jsonObject.put("pageData", resultList);
				state = new ResultState(ResultType.SUCCESS, "获取推荐评论成功");
			} else {
				/*
				有该参数，说明是查看作品的评论或者回复
				 */
				if (orderBy.equals(DaoEnum.ORDER_BY_LIKE) ||
						orderBy.equals(DaoEnum.ORDER_BY_TIME)) {
					//判断是按时间获取还是按点赞数获取
					vo.setOrder(orderBy);
					//具体是评论还是回复要看有无targetId，但是两种请求调用的方法相同，去下一层判断
					PageBo<CommentDto> resultPageBo =
							commentService.getCommentListByPage(vo, currentPage);
					List<CommentDto> list = resultPageBo.getList();
					if (list == null || list.size() == 0) {
						state = new ResultState(ResultType.NO_RECORD, "当前页没有评论记录");

					} else {
						state = new ResultState(ResultType.SUCCESS, "获取评论记录成功");
					}
					jsonObject.put("pageData", resultPageBo);

				} else {
					//参数异常
					logger.error("orderBy参数错误：order =" + orderBy);
					ResponseChoose.respWrongParameterError(resp, "获取评论orderBy参数：" + orderBy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "获取评论时出现异常");
		}
		jsonObject.put("state", state);
		logger.debug(jsonObject);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	@RequestMapping(value = "/WritingCommentServlet", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		JSONObject params = GetParamChoose.getJsonByJson(req);
		Comment comment = GetParamChoose.getObjByParam(params, Comment.class);
		logger.trace("发表评论 comment=" + comment);


		Long userId = WebUtil.getUserId(req);
		if (userId == null) {
			logger.error("评论时用户未登录");
			ResponseChoose.respUserUnloggedError(resp);
			return;
		} else if (comment == null){
			logger.error("获取不到评论数据");
			ResponseChoose.respNoParameterError(resp, "获取不到评论数据");
			return;
		}
		comment.setUserid(userId);

		String type = params.getString("type");
		if (TYPE_ARTICLE.equals(type)) {
			logger.trace("发表文章的评论");
			commentService.setTableName(Article.class);

		} else if (TYPE_POSTS.equals(type)) {
			logger.trace("发表问贴的评论");
			commentService.setTableName(Posts.class);
		} else {
			logger.error("评论type参数错误：" + type);
			ResponseChoose.respWrongParameterError(resp, "评论type参数错误");
			return;
		}
		ResultState state;

		//敏感词过滤
		SensitiveUtil.filterComment(new SensitiveFilter(), comment);

		//html防注入
//		SecurityUtil.ensureJsSafe(comment);
		SecurityUtil.htmlEncode(comment);
		try {
			ResultType resultType = commentService.publishNewComment(comment);
			state = new ResultState(resultType, "发表结果");
		} catch (Exception e) {
			e.printStackTrace();
			ResultType resultType = ResultType.EXCEPTION;
			state = new ResultState(resultType, "异常");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", state);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	@RequestMapping(value = "/WritingCommentServlet", method = RequestMethod.DELETE)
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		JSONObject paramJson = GetParamChoose.getJsonByJson(req);
		logger.trace("删除评论 paramJson: " + paramJson);
		Long articleId = paramJson.getLong("article");
		Long postsId = paramJson.getLong("posts");

		Long userId = WebUtil.getUserId(req);
		if (userId == null) {
			logger.error("删除评论时用户未登录");
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		ResultState state;
		try {
			if (articleId != null) {
				//删除文章表中的评论记录
				commentService.setTableName(Article.class);
				ResultType resultType = commentService.deleteComment(articleId, userId);
				state = new ResultState(resultType, "删除结果");
			} else {
				//删除问贴表中的评论记录
				commentService.setTableName(Posts.class);
				ResultType resultType = commentService.deleteComment(postsId, userId);
				state = new ResultState(resultType, "删除结果");
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "删除异常");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", state);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
}
