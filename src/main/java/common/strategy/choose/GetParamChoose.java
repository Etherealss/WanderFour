package common.strategy.choose;

import com.alibaba.fastjson.JSONObject;
import common.strategy.GetParamStrategy;
import common.strategy.impl.GetParamStrategyImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 寒洲
 * @description 获取参数
 * @date 2020/10/16
 */
public class GetParamChoose {

	private static Logger logger = Logger.getLogger(GetParamChoose.class);

	private static GetParamStrategy strategy = new GetParamStrategyImpl();

	/**
	 * 通过前端的json数据获取json对象
	 * @param req
	 * @return 封装了参数的json对象
	 */
	public static JSONObject getJsonByJson(HttpServletRequest req) {
		return strategy.getJsonByJson(req);
	}

	/**
	 * 通过url数据获取json对象
	 * @param req
	 * @return 封装了参数的json对象
	 */
	public static JSONObject getJsonByUrl(HttpServletRequest req) {
		return strategy.getJsonByUrl(req);
	}

	/**
	 * 通过表单数据获取Java对象
	 * @param req
	 * @param clazz
	 * @param <T>   转化的Java类型
	 * @return 封装了参数的Java对象
	 */
	public static <T> T getObjByForm(HttpServletRequest req, Class<T> clazz) {
		return strategy.getObjByForm(req, clazz);
	}

	/**
	 * 通过Json数据获取Java对象
	 * @param req
	 * @param clazz
	 * @param <T>   转化的Java类型
	 * @return 封装了参数的Java对象 获取失败返回null
	 */
	public static <T> T getObjByJson(HttpServletRequest req, Class<T> clazz) {
		/*
		获取json格式传输过来的参数
		此处因为不用req.getParameter，
		所以要先获取json，再用json封装对象
		 */
		JSONObject jsonByJson = strategy.getJsonByJson(req);
		try {
			return strategy.getObjByParam(jsonByJson, clazz);
		} catch (Exception ex) {
			logger.error("获取实例失败", ex);
		}
		return null;
	}

	/**
	 * 通过json参数集合封装对象
	 * @param jsonObject
	 * @param clazz
	 * @param <T>
	 * @return 获取失败返回null
	 */
	public static <T> T getObjByParam(JSONObject jsonObject, Class<T> clazz) {
		return strategy.getObjByParam(jsonObject, clazz);
	}


}
