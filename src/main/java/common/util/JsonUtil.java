package common.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/15
 */
public class JsonUtil {

	/**
	 * 合并两个json
	 * @param srcObj
	 * @param addObj
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject combineJson(JSONObject srcObj, JSONObject addObj) throws JSONException {
		Set<Map.Entry<String, Object>> entries = addObj.entrySet();
		for (Map.Entry<String, Object> en : entries){
			srcObj.put(en.getKey(), en.getValue());
		}
		return srcObj;
	}
}
