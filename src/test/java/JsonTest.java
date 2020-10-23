import com.alibaba.fastjson.JSONObject;
import pojo.bean.CommentBean;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.dto.ReplyDto;
import pojo.dto.ResultState;
import common.enums.ResultType;
import org.junit.Test;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.User;
import common.util.TestUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/15
 */
public class JsonTest {

	@Test
	public void testToJSONString() {
		//储存枚举输出
		ResultType resultType = ResultType.SUCCESS;
		String jsonString = JSONObject.toJSONString(resultType);
		System.out.println(jsonString); //"SUCCESS"
		User user = new User("123", "123");
		String userstr = JSONObject.toJSONString(user);
		System.out.println(userstr); //{"password":"123","userid":"123"}
	}

	@Test
	public void testOutput() {
		//json输出
		ResultType resultType = ResultType.SUCCESS;
		// java.lang.String cannot be cast to com.alibaba.fastjson.JSONObject
//		JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(resultState);
		User user = new User("123", "123");
		JSONObject jsonObject2 = (JSONObject) JSONObject.toJSON(user);
		System.out.println(jsonObject2); // {"password":"123","userid":"123"}
	}

	@Test
	public void testPut() {
		// 储存多个对象
		ResultType state1 = ResultType.SUCCESS;
		ResultType state2 = ResultType.EXCEPTION;
		Article defaultArticlePo = TestUtil.getDefaultArticlePo();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", state1);
		jsonObject.put("article", defaultArticlePo);
		System.out.println(jsonObject);
		//key相同会覆盖
		jsonObject.put("state", state2);
		System.out.println(jsonObject);
	}

	@Test
	public void testCombine() {
		// 合并JSONObject
		ResultType state1 = ResultType.SUCCESS;
		Article defaultArticlePo = TestUtil.getDefaultArticlePo();
		JSONObject jsonObj1 = new JSONObject();
		jsonObj1.put("state", state1);
		jsonObj1.put("article", defaultArticlePo);
		System.out.println(jsonObj1);

		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("state", ResultType.EXCEPTION);
		JSONObject jsonObj3 = new JSONObject();
		jsonObj3.put("json1", jsonObj1);
		jsonObj3.put("json2", jsonObj2);
		System.out.println(jsonObj3);
	}

	@Test
	public void testDto() {
		ResultState result = new ResultState(ResultType.SUCCESS, "响应结果");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", result);
		System.out.println(jsonObject);
	}

	@Test
	public void testOutput2() {
		ResultState info = new ResultState(ResultType.SUCCESS, "登录结果");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", info);
		//{"state":{"code":"SUCCESS","msg":"登录结果"}}
		System.out.println(jsonObject);
	}

	@Test
	public void testObject() {
//		JSONObject map = new JSONObject();
//		String[] params = {};
//		int i=0;
//		for (Field field : User.class.getDeclaredFields()) {
//			String key = field.getName();
//			map.put(key,params[i++]);
//			if (i == params.length)
//				break;
//		}
	}

	@Test
	public void testArticle() {
		Article article = TestUtil.getDefaultArticlePo();
		System.out.println(article.getPartition());
	}

	@Test
	public void testListToJson() {
		List<Article> list = new ArrayList<>();
		list.add(TestUtil.getDefaultArticlePo());
		list.add(TestUtil.getDefaultArticlePo());
		list.add(TestUtil.getDefaultArticlePo());
		JSONObject json = new JSONObject();
		json.put("list", list);
		System.out.println(json);
	}

	@Test
	public void testCommentToJson() {
		PageBean<CommentDto> pageBean = new PageBean<>();
		pageBean.setCurrentPage(1);
		pageBean.setTotalPage(6);
		pageBean.setTotalCount(52L);
		pageBean.setRows(3);

		List<CommentDto> list = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			CommentDto dto = TestUtil.getDeultCommentDto();
			list.add(dto);
		}
		pageBean.setList(list);

		JSONObject json = new JSONObject();
		json.put("pageBean", pageBean);
		System.out.println(json);
	}

	@Test
	public void testReplyToJson() {
		PageBean<ReplyDto> pageBean = new PageBean<>();
		pageBean.setCurrentPage(1);
		pageBean.setTotalPage(5);
		pageBean.setTotalCount(14L);
		pageBean.setRows(3);
		List<ReplyDto> list = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			ReplyDto dto = TestUtil.getDeultReplyDto();
			list.add(dto);
		}
		pageBean.setList(list);

		JSONObject json = new JSONObject();
		json.put("pageBean", pageBean);
		System.out.println(json);
	}
}
