package service.impl;

import static org.junit.Assert.*;

import com.alibaba.fastjson.JSONObject;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.dto.CommentDto;
import pojo.po.Article;
import pojo.po.Comment;
import service.CommentService;

import java.util.List;

public class CommentServiceImplTestTest {

	private Logger logger = Logger.getLogger(CommentServiceImpl.class);

	CommentService service = ServiceFactory.getArticleCommentService();
	@Test
	public void testGetHotCommentList() throws Exception {
		List<CommentDto> list = service.getHotCommentList(1, 3, 3, 1L, 1L);
		JSONObject json = new JSONObject();
		json.put("CommentData", list);
		logger.debug(json);
	}

	@Test
	public void testGetCommentListByPage() {
	}
}