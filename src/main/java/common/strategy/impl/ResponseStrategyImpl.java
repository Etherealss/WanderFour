package common.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultType;
import common.strategy.ResponseStrategy;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 寒洲
 * @description 返回浏览器的策略实现
 * @date 2020/10/17
 */
public class ResponseStrategyImpl implements ResponseStrategy {

	private Logger logger = Logger.getLogger(ResponseStrategyImpl.class);

	@Override
	public void respToBrowser(HttpServletResponse response, ResultState state) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", state);
		//发送给客户端
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void respToBrowser(HttpServletResponse response, JSONObject jsonObject) {
		//发送给客户端
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void respOnlyStateToBrowser(HttpServletResponse response, ResultType resultType, String msg) {
		//发送给客户端
		JSONObject jsonObject = new JSONObject();
		ResultState state = new ResultState(resultType, msg);
		jsonObject.put("state", state);
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
