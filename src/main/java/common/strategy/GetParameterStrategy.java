package common.strategy;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/16
 */
public interface GetParameterStrategy {

	/**
	 * 通过前端的json数据获取json对象
	 * @param req
	 * @return 封装了参数的json对象
	 */
	JSONObject getJsonByJson(HttpServletRequest req);

	/**
	 * 通过url数据获取json对象
	 * @param req
	 * @return 封装了参数的json对象
	 */
	JSONObject getJsonByUrl(HttpServletRequest req);

	/**
	 * 通过表单数据获取Java对象
	 * @param req
	 * @param clazz
	 * @param <T> 转化的Java类型
	 * @return 封装了参数的Java对象
	 */
	<T> T getForm(HttpServletRequest req, Class<T> clazz);
}
