package common.strategy;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/16
 */
public interface GetParamStrategy {

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
	<T> T getObjByForm(HttpServletRequest req, Class<T> clazz);

	/**
	 * 通过传入的json参数集合获取指定对象
	 * @param jsonObject json参数集合
	 * @param clazz
	 * @param <T>
	 * @return 封装的实例
	 */
	<T> T getObjByParam(JSONObject jsonObject, Class<T> clazz);

	/**
	 * 获取表单数据并包装在json中
	 * json的键为参数名，值为参数数组的第一个元素
	 * @param req
	 * @return
	 */
	JSONObject getJsonByForm(HttpServletRequest req);
}
