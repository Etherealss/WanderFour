package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.dto.CommentDto;
import pojo.po.Article;
import service.CommentService;

import java.util.List;

public class CommentServiceImplTestTest {

	private Logger logger = Logger.getLogger(CommentServiceImpl.class);

	CommentService service = ServiceFactory.getCommentService(Article.class);
	@Test
	public void testGetHotCommentList() throws Exception {
		List<CommentDto> list = service.getHotCommentList(1L, 1L);
		JSONObject json = new JSONObject();
		json.put("CommentData", list);
		logger.debug(json);
	}

	@Test
	public void testGetCommentListByPage() {
	}
}