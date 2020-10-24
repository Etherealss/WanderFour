package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.CommentEnum;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.dto.ResultState;
import pojo.po.Article;
import pojo.po.Comment;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章评论/回复
 * @date 2020/10/23
 */
@WebServlet("/ArticleCommentServlet")
public class ArticleCommentController extends BaseServlet {

	private Logger logger = Logger.getLogger(ArticleCommentController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取文章评论");
		JSONObject param = GetParamChoose.getJsonByUrl(req);
		//空参检查
		if (param == null) {
			ResponseChoose.respNoParameterError(resp, "获取文章评论");
			return;
		}
		//获取请求的数据
		CommentService service = ServiceFactory.getCommentService(Article.class);

		JSONObject jsonObject = new JSONObject();
		ResultState state;
		try {
			String orderBy = param.getString("orderBy");
			Long targetId = param.getLong("targetId");
			Long parentId = param.getLong("parentId");
			Long userid = param.getLong("userid");

			CommentVo vo = new CommentVo();
			vo.setParentId(parentId);
			//如果targetId没有参数，设置为null，不影响
			vo.setTargetId(targetId);
			vo.setUserid(userid);

			if (orderBy == null) {
				/*
				没有该参数，说明是获取文章的推荐评论
				 */
				List<CommentDto> resultList = service.getHotCommentList(parentId, userid);
				assert resultList != null;
				jsonObject.put("commentData", resultList);

			} else {
				/*
				有该参数，说明是查看文章的评论或者回复
				 */
				if (orderBy.equals(CommentEnum.ORDER_BY_LIKE) || orderBy.equals(CommentEnum.ORDER_BY_TIME)){
					//判断是按时间获取还是按点赞数获取
					vo.setOrder(orderBy);
					//具体是评论还是回复要看有无targetId，但是两种请求调用的方法相同，去下一层判断
					PageBean<CommentDto> resultPageBean =
							service.getCommentListByPage(vo, param.getIntValue("currentPage"));
				} else {
					//参数异常
					logger.error("orderBy参数错误：order =" + orderBy);
					throw new Exception("orderBy参数错误：order =" + orderBy);
				}

			}

			state = new ResultState(ResultType.SUCCESS, "获取评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			state = new ResultState(ResultType.EXCEPTION, "获取评论时出现异常");
		}
		jsonObject.put("state", state);
		ResponseChoose.respToBrowser(resp, jsonObject);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("发表文章评论");
		Comment comment = GetParamChoose.getObjByForm(req, Comment.class);

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("删除文章评论");
		super.doDelete(req, resp);
	}
}
