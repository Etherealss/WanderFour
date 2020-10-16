package common.util;

import com.alibaba.fastjson.JSONObject;
import common.dto.ResultState;
import common.enums.ResultMsg;
import common.enums.ResultType;
import org.apache.log4j.Logger;
import pojo.po.Writing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class ControllerUtil {

	private static Logger logger = Logger.getLogger(ControllerUtil.class);

//	public static void setWritingWithData(HttpServletRequest req, Writing p) {
//		//TODO 字符串还是数字
//		String partition = req.getParameter("partition");
//		String category = req.getParameter("category");
//		String authorId = req.getParameter("authorId");
//		String title = req.getParameter("title");
//		String content = req.getParameter("content");
//		String label1 = req.getParameter("label1");
//		String label2 = req.getParameter("label2");
//		String label3 = req.getParameter("label3");
//		String label4 = req.getParameter("label4");
//		String label5 = req.getParameter("label5");
//		// 赋值
//		p.setPartitionStr(partition);
//		p.setCategory(category);
//		p.setAuthorId(authorId);
//		p.setTitle(title);
//		p.setContent(content);
//		p.setLabel1(label1);
//		p.setLabel2(label2);
//		p.setLabel3(label3);
//		p.setLabel4(label4);
//		p.setLabel5(label5);
//	}

	/**
	 * 传输返回给浏览器的json包
	 * @param response
	 * @param jsonObject 返回给浏览器的json包
	 */
	public static void respToBrowser(HttpServletResponse response, JSONObject jsonObject) {
		//发送给客户端
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 传输返回给浏览器的json包
	 * @param response
	 * @param state    业务响应结果
	 */
	public static void respToBrowser(HttpServletResponse response, ResultState state) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", state);
		//发送给客户端
		try {
			response.getWriter().write(jsonObject.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 只传输ResultState给浏览器
	 * @param response
	 * @param resultType 状态码枚举
	 */
	public static void respOnlyStateToBrowser(HttpServletResponse response, ResultType resultType, String msg) {
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

	/**
	 * 获取前端传来的Form格式数据
	 * @param request
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getForm(HttpServletRequest request, Class<T> clazz) {
		try {
			JSONObject json = new JSONObject();
			for (Field field : clazz.getDeclaredFields()) {
				String key = field.getName();
				String parameter = request.getParameter(key);
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

	/**
	 * 获取前端传来的Json格式的数据
	 * @param req
	 * @return
	 */
	public static JSONObject getJson(HttpServletRequest req) {
		try {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				responseStrBuilder.append(inputStr);
			}

			return JSONObject.parseObject(responseStrBuilder.toString());
		} catch (Exception e) {
			logger.error("解析json 失败 " + e.getMessage());
		}
		return null;
	}

	/**
	 * 获取GET方式访问的参数
	 * @param req
	 * @return
	 */
	public static JSONObject getJsonByUrl(HttpServletRequest req) {
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
		}
		return null;
	}

	/**
	 * 空参异常，返回前端
	 * @param resp
	 * @throws ServletException
	 */
	public static void respNoParameterEeeor(HttpServletResponse resp) throws ServletException {
		ControllerUtil.respOnlyStateToBrowser(resp, ResultType.ERROR, ResultMsg.NO_PARAMETER);
		throw new ServletException(ResultMsg.NO_PARAMETER);
	}

	/**
	 * 空参异常，返回前端
	 * @param resp
	 * @param msg  描述性字符串
	 * @throws ServletException
	 */
	public static void respNoParameterEeeor(HttpServletResponse resp, String msg) throws ServletException {
		// 拼接字符串结果举例： 注册 异常！没有获取到参数
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		ControllerUtil.respOnlyStateToBrowser(resp, ResultType.ERROR, "[" + msg + "]" + ResultMsg.NO_PARAMETER);
		throw new ServletException(msg + ResultMsg.NO_PARAMETER);
	}
}
