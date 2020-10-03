package controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 用户设置
 * @date 2020/10/3
 */
@WebServlet("/UserSettingServlet")
public class UserSettingController  extends BaseUserServlet{

	public void changePw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("用户修改密码...");
	}
}
