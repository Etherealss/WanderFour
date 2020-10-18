package common.strategy;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultType;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 寒洲
 * @description Controller返回浏览器的策略
 * @date 2020/10/17
 */
public interface ResponseStrategy {

	/**
	 * 返回ResultState信息包给浏览器
	 * @param response
	 * @param state
	 */
	void respToBrowser(HttpServletResponse response, ResultState state);

	/**
	 * 返回json信息包给浏览器
	 * @param response
	 * @param jsonObject
	 */
	void respToBrowser(HttpServletResponse response, JSONObject jsonObject);

	/**
	 * 只返回转态state给浏览器
	 * @param response
	 * @param resultType
	 * @param msg
	 */
	void respOnlyStateToBrowser(HttpServletResponse response, ResultType resultType, String msg);
}
