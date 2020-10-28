package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.ResultType;
import common.enums.TargetType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.ControllerUtil;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.dto.ResultState;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章/帖子的评论回复
 * @date 2020/10/23
 */
@WebServlet("/WritingCommentServlet")
public class WritingCommentController extends BaseServlet {

	private Logger logger = Logger.getLogger(WritingCommentController.class);

	private final static String TYPE_ARTICLE = TargetType.ARTICLE.val();
	private final static String TYPE_POSTS = TargetType.POSTS.val();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取评论");
		JSONObject param = GetParamChoose.getJsonByUrl(req);
		//空参检查
		if (param == null) {
			ResponseChoose.respNoParameterError(resp, "获取文章评论");
			return;
		}
		//判断文章还是帖子
		String type = param.getString("type");
		//获取请求的数据
		CommentService service;
		if (TYPE_ARTICLE.equals(type)) {
			logger.trace("获取文章的评论");
			service = ServiceFactory.getCommentService(Article.class);
		} else if (TYPE_POSTS.equals(type)) {
			logger.trace("获取问贴的评论");
			service = ServiceFactory.getCommentService(Posts.class);
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
			Long userid = param.getLong("userid");
			int currentPage = param.getIntValue("currentPage");

			CommentVo vo = new CommentVo();
			vo.setParentId(parentId);
			//如果targetId没有参数，设置为null，不影响
			vo.setTargetId(targetId);
			vo.setUserid(userid);
			if (orderBy == null) {
				/*
				没有该参数，说明是获取作品的推荐评论
				 */
				List<CommentDto> resultList = service.getHotCommentList(parentId, userid);
				assert resultList != null;
				jsonObject.put("commentData", resultList);
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
					PageBean<CommentDto> resultPageBean =
							service.getCommentListByPage(vo, currentPage);
					List<CommentDto> list = resultPageBean.getList();
					logger.debug(list);
					if (list == null || list.size() == 0) {
						state = new ResultState(ResultType.NO_RECORD, "当前页没有评论记录");

					} else {
						state = new ResultState(ResultType.SUCCESS, "获取评论记录成功");
					}
					jsonObject.put("pageData", resultPageBean);

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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Comment comment = GetParamChoose.getObjByForm(req, Comment.class);
		logger.trace("发表评论 comment=" + comment);
		String type = req.getParameter("type");

		Long userId = ControllerUtil.getUserId(req);
		if (userId == null) {
			logger.error("评论时用户未登录");
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}
		comment.setUserid(userId);


		CommentService service;
		if (TYPE_ARTICLE.equals(type)) {
			logger.trace("发表文章的评论");
			service = ServiceFactory.getCommentService(Article.class);

		} else if (TYPE_POSTS.equals(type)) {
			logger.trace("发表问贴的评论");
			service = ServiceFactory.getCommentService(Posts.class);
		} else {
			logger.error("评论type参数错误：" + type);
			ResponseChoose.respWrongParameterError(resp, "评论type参数错误");
			return;
		}
		ResultState state;
		try {
			ResultType resultType = service.publishNewComment(comment);
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

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject paramJson = GetParamChoose.getJsonByJson(req);
		logger.trace("删除评论 paramJson: " + paramJson);
		Long articleId = paramJson.getLong("article");
		Long postsId = paramJson.getLong("posts");

		Long userId = ControllerUtil.getUserId(req);
		if (userId == null) {
			logger.error("评论时用户未登录");
			ResponseChoose.respUserUnloggedError(resp);
			return;
		}

		ResultState state;
		try {
			if (articleId != null) {
				//删除文章表中的评论记录
				CommentService service = ServiceFactory.getCommentService(Article.class);
				ResultType resultType = service.deleteComment(articleId, userId);
				state = new ResultState(resultType, "删除结果");
			} else {
				//删除问贴表中的评论记录
				CommentService service = ServiceFactory.getCommentService(Posts.class);
				ResultType resultType = service.deleteComment(postsId, userId);
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
}
