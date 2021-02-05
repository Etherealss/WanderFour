package common.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import common.strategy.GetParamStrategy;
import common.util.SecurityUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 寒洲
 * @description 获取参数的策略
 * @date 2020/10/16
 */
public class GetParamStrategyImpl implements GetParamStrategy {

	private Logger logger = Logger.getLogger(GetParamStrategyImpl.class);

	@Override
	public JSONObject getJsonByJson(HttpServletRequest req) {
		try {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(
					req.getInputStream(), StandardCharsets.UTF_8));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				responseStrBuilder.append(inputStr);
			}
			JSONObject resultJson = JSONObject.parseObject(responseStrBuilder.toString());
			if (resultJson != null) {
				JSONObject returnJson = SecurityUtil.ensureJsSafe(resultJson);
			}
			return resultJson;
		} catch (Exception e) {
			logger.error("解析json 失败 " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject getJsonByUrl(HttpServletRequest req) {
		try {
			String urlParams = req.getQueryString();
			if ("".equals(urlParams) || urlParams == null) {
				logger.error("无参数");
				return null;
			}
			//切割 & ，得到一个个键值对
			String[] params = urlParams.split("&");
			JSONObject json = new JSONObject();
			for (String p : params) {
				//切割键值对，存到json中
				String[] kv = p.split("=");
				json.put(kv[0], kv[1]);
			}
			return json;
		} catch (Exception e) {
			logger.error("解析json 失败 " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T getObjByForm(HttpServletRequest req, Class<T> clazz) {
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
			logger.error("解析 formData 失败。Message：" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T getObjByParam(JSONObject jsonObject, Class<T> clazz) {
		try {
			JSONObject json = new JSONObject();
			for (Field field : clazz.getDeclaredFields()) {
				//获取对象属性名
				String key = field.getName();
				//获取属性值参数
				Object parameter = jsonObject.get(key);

				json.put(key, parameter);
			}
			//封装对象返回
			return json.toJavaObject(clazz);
		} catch (Exception e) {
			logger.error("封装对象失败");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject getJsonByForm(HttpServletRequest req) {
		JSONObject json = new JSONObject();
		Map<String, String[]> parameterMap = req.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			json.put(entry.getKey(), entry.getValue()[0]);
		}
		return json;
	}
}
