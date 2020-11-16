package common.util;

import common.strategy.choose.ResponseChoose;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class ControllerUtil {

	/**
	 * 从req中获取用户的userid
	 * @param req
	 * @return
	 */
	public static Long getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object userid = session.getAttribute("userid");
		return (Long) userid;
	}

	/**
	 * 接口参数判空
	 * @param resp
	 * @param msg
	 * @param params
	 * @return 参数缺失返回true
	 * @throws ServletException
	 */
	public static boolean isParamMissing(HttpServletResponse resp, String msg, String... params) throws ServletException {
		if (params == null){
			ResponseChoose.respNoParameterError(resp, msg + " Json为null，获取不到Json参数");
			return true;
		}
		for (String param : params) {
			if ("".equals(param) || param == null) {
				ResponseChoose.respNoParameterError(resp, msg + "参数缺失");
				return true;
			}
		}
		return false;
	}
}