package controller;

import common.factory.ServiceFactory;
import common.strategy.choose.GetParamChoose;
import org.apache.log4j.Logger;
import pojo.po.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 文章评论/回复
 * @date 2020/10/23
 */
@WebServlet("/ArticleCommentServlet")
public class ArticleCommentController extends BaseServlet{

	private Logger logger = Logger.getLogger(ArticleCommentController.class);


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取文章评论");
		super.doGet(req, resp);
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
