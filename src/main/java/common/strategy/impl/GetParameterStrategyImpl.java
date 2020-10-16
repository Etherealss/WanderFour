package common.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import common.strategy.GetParameterStrategy;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @author 寒洲
 * @description 获取参数的策略
 * @date 2020/10/16
 */
public class GetParameterStrategyImpl implements GetParameterStrategy {

	private Logger logger = Logger.getLogger(GetParameterStrategyImpl.class);

	@Override
	public JSONObject getJsonByJson(HttpServletRequest req) {
		return null;
	}

	@Override
	public JSONObject getJsonByUrl(HttpServletRequest req) {
		return null;
	}

	@Override
	public <T> T getForm(HttpServletRequest req, Class<T> clazz) {
		try {
			JSONObject json = new JSONObject();
			for (Field field : clazz.getDeclaredFields()) {
				String key = field.getName();
				String parameter = req.getParameter(key);
				json.put(key, parameter);
			}
			logger.trace(json);
			return json.toJavaObject(clazz);
		} catch (Exception ex) {
			logger.error("解析 formData 失败 " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}
