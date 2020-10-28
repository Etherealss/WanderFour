package common.util;

import javax.servlet.http.HttpServletRequest;
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
}
