package common.util;

import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.strategy.choose.ResponseChoose;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class WebUtil {

	/**
	 * 从req中获取用户的userid
	 * @param req
	 * @return
	 */
	public static Long getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object userid = session.getAttribute(AttrEnum.LOGIN_SESSION_NAME);
		return (Long) userid;
	}

	/**
	 * 接口参数判空
	 * @param resp
	 * @param params 参数的json对象
	 * @param msg
	 * @param paramNames 参数名
	 * @return 参数缺失返回true，没有参数缺失，返回false
	 * @throws ServletException
	 */
	public static boolean isParamMissing(HttpServletResponse resp, JSONObject params,
	                                     String msg, String... paramNames) throws ServletException {
		if (params == null) {
			ResponseChoose.respNoParameterError(resp, msg + " JSONObject为null，获取不到Json参数");
			// 确认参数缺失（is missing）返回true
			return true;
		}
		for (String name : paramNames) {
			String param = params.getString(name);
			if ("".equals(param) || param == null) {
				ResponseChoose.respNoParameterError(resp, msg + " '" + name + "' 参数缺失");
				// 确认参数缺失（is missing）返回true
				return true;
			}
		}
		// 没有参数缺失，返回false
		return false;
	}

}
