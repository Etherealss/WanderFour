package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.ResultType;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.CommentVo;
import pojo.bo.PageBo;
import pojo.dto.CommentDto;
import pojo.po.Comment;
import pojo.po.Posts;
import service.CommentService;

public class CommentServiceImplTestTest {

	private Logger logger = Logger.getLogger(CommentServiceImpl.class);

	CommentService service = ServiceFactory.getCommentService(Posts.class);
	@Test
	public void testGetHotCommentList() throws Exception {
		PageBo<CommentDto> list = service.getHotCommentList(1L, 1L);
		JSONObject json = new JSONObject();
		json.put("pageData", list);
		logger.debug(json);
	}

	@Test
	public void testGetCommentListByPage() throws Exception {
		CommentVo vo = new CommentVo();
		// userid parentId commentRows replyRows orderBy
		vo.setUserid(1L);
		vo.setParentId(1L);
		vo.setCommentRows(10);
		vo.setReplyRows(3);
		vo.setOrder(DaoEnum.ORDER_BY_TIME);

		PageBo<CommentDto> pageBo = service.getCommentListByPage(vo, 1);
		JSONObject json = new JSONObject();
		json.put("pageBean", pageBo);
		logger.debug(json);
	}

	@Test
	public void testCreate() throws Exception {
		Comment comment = new Comment();
		comment.setContent("这是一条评论/回复");
		comment.setParentId(2L);
//		comment.setTargetId(2L);
		comment.setUserid(1L);
		ResultType resultType = service.publishNewComment(comment);
		logger.debug(resultType);
	}

	@Test
	public void testDelete() throws Exception {
		ResultType resultType = service.deleteComment(3L, 3L);
		logger.debug(resultType);
	}
}