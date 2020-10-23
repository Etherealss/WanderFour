package controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 问贴评论
 * @date 2020/10/23
 */
public class PostsCommentController extends BaseServlet{

	private Logger logger = Logger.getLogger(PostsCommentController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("获取问贴评论");
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("发表问贴评论");
		super.doPost(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("删除问贴评论");
		super.doDelete(req, resp);
	}
}
