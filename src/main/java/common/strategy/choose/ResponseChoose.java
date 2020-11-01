package common.strategy.choose;

import com.alibaba.fastjson.JSONObject;
import pojo.dto.ResultState;
import common.enums.ResultMsg;
import common.enums.ResultType;
import common.strategy.ResponseStrategy;
import common.strategy.impl.ResponseStrategyImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 寒洲
 * @description 返回浏览器的策略选择
 * @date 2020/10/17
 */
public class ResponseChoose {

	/**
	 * 策略类
	 */
	private static ResponseStrategy strategy = new ResponseStrategyImpl();


	/**
	 * 返回ResultState信息包给浏览器
	 * @param resp
	 * @param state
	 */
	public static void respToBrowser(HttpServletResponse resp, ResultState state) {
		strategy.respToBrowser(resp, state);
	}

	/**
	 * 返回json信息包给浏览器
	 * @param resp
	 * @param json
	 */
	public static void respToBrowser(HttpServletResponse resp, JSONObject json) {
		strategy.respToBrowser(resp, json);
	}

	/**
	 * 只返回状态类型state给浏览器
	 * @param resp
	 * @param resultType
	 * @param msg
	 */
	public static void respOnlyStateToBrowser(HttpServletResponse resp, ResultType resultType, String msg) {
		if (resultType == ResultType.EXCEPTION) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		strategy.respOnlyStateToBrowser(resp, resultType, msg);
	}

	/**
	 * 遇到空参错误，返回错误状态给浏览器
	 * @param resp
	 * @param msg  附加信息
	 * @throws ServletException
	 */
	public static void respNoParameterError(HttpServletResponse resp, String msg) throws ServletException {
		//状态码 402
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		strategy.respOnlyStateToBrowser(resp, ResultType.ERROR,
				ResultMsg.NO_PARAMETER + msg);
		throw new ServletException(ResultMsg.NO_PARAMETER + msg);
	}


	/**
	 * 获取不到对象，返回404状态给浏览器
	 * @param resp
	 * @param msg  附加信息
	 * @throws ServletException
	 */
	public static void respUnFoundError(HttpServletResponse resp, String msg) throws ServletException {
		//状态码 404
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		strategy.respOnlyStateToBrowser(resp, ResultType.NO_RECORD,
				ResultMsg.UN_FOUND + msg);
		throw new ServletException(ResultMsg.UN_FOUND + msg);
	}

	/**
	 * 后端异常，返回500状态给浏览器
	 * @param resp
	 * @param msg  附加信息
	 * @throws ServletException
	 */
	public static void respException(HttpServletResponse resp, String msg) {
		//状态码 500
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		strategy.respOnlyStateToBrowser(resp, ResultType.EXCEPTION, msg);
	}

	/**
	 * 遇到参数错误，返回错误状态给浏览器
	 * @param resp
	 * @throws ServletException
	 */
	public static void respWrongParameterError(HttpServletResponse resp, String msg) throws ServletException {
		// 状态码 400
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		strategy.respOnlyStateToBrowser(resp, ResultType.ERROR,
				ResultMsg.WRONG_PARAMETER + msg);
		throw new ServletException(ResultMsg.WRONG_PARAMETER + msg);
	}

	/**
	 * 用户未登录，返回错误状态给浏览器
	 * @param resp
	 * @throws ServletException
	 */
	public static void respUserUnloggedError(HttpServletResponse resp) throws ServletException {
		//状态码 未授权 401
		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		strategy.respOnlyStateToBrowser(resp, ResultType.NOT_LOGGED, "用户未登录");
	}

}
