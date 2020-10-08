package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 寒洲
 * @description 用户设置
 * @date 2020/10/3
 */
@WebServlet("/UserSettingServlet")
public class UserSettingController extends BaseServlet {

	public void changePw(Map<String, Object> info, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.trace("用户修改密码...");
	}
}
