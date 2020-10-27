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
	public static void respToBrowser(HttpServletResponse resp, ResultState state){
		strategy.respToBrowser(resp, state);
	}

	/**
	 * 返回json信息包给浏览器
	 * @param resp
	 * @param json
	 */
	public static void respToBrowser(HttpServletResponse resp, JSONObject json){
		strategy.respToBrowser(resp, json);
	}

	/**
	 * 只返回状态类型state给浏览器
	 * @param resp
	 * @param resultType
	 * @param msg
	 */
	public static void respOnlyStateToBrowser(HttpServletResponse resp, ResultType resultType, String msg){
		strategy.respOnlyStateToBrowser(resp, resultType, msg);
	}

	/**
	 * 遇到空参错误，返回错误状态给浏览器
	 * @param resp
	 * @param msg 附加信息
	 * @throws ServletException
	 */
	public static void respNoParameterError(HttpServletResponse resp, String msg) throws ServletException{
		// 拼接字符串结果举例： 注册 异常！没有获取到参数
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		strategy.respOnlyStateToBrowser(resp, ResultType.ERROR,
					ResultMsg.NO_PARAMETER + msg);
		throw new ServletException(ResultMsg.NO_PARAMETER + msg);
	}

	/**
	 * 遇到参数错误，返回错误状态给浏览器
	 * @param resp
	 * @throws ServletException
	 */
	public static void respWrongParameterError(HttpServletResponse resp, String msg) throws ServletException{
		strategy.respOnlyStateToBrowser(resp, ResultType.ERROR,
				ResultMsg.WRONG_PARAMETER + msg);
		throw new ServletException(ResultMsg.WRONG_PARAMETER + msg);
	}
}
