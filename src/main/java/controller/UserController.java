package controller;

import common.bean.User;
import org.apache.log4j.Logger;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 寒洲
 * @description 登录注册操作
 * @date 2020/10/2
 */
@WebServlet("/UserEnterServlet")
public class UserController extends BaseUserServlet{

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("用户登录...");
//		Long userid = Long.valueOf(req.getParameter("userid"));
//		String password = req.getParameter("password");
//		UserService us = new UserServiceImpl();
//		int code = us.validateUserLogin(userid, password);
		String key = req.getParameter("key");
		logger.info(key);
		int code = 200;
		String msg;
		//判断是否登录成功的boolean
		info.put("code", code);
		switch (code){
			case 200:
				msg="登录成功";
				break;
			case 404:
				msg = "账号不存在";
				break;
			case 400:
				msg = "密码错误";
				break;
			default:
				msg = "出现异常";
				break;
		}
		info.put("msg",msg);

		responseToBrowser(resp);
	}

	public void register(HttpServletRequest req, HttpServletResponse resp){
		logger.info("用户注册...");
		try {
			//获取请求参数
			String nickname = req.getParameter("regi-nickname");
			Long id = Long.valueOf(req.getParameter("regi-userid"));
			String password = req.getParameter("regi-pw1");
			//将获取的生日字符串转换为日期类型
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").
					parse(req.getParameter("regi-birthday"));
			String userType = req.getParameter("userType");
			String sex_str = req.getParameter("regi-sex");
			//判断性别，小哥哥为true
			Boolean sex = "male".equals(sex_str);
			//根据性别加载默认头像
			// 封装到user对象中
			String avatarPath;
			if (sex) {
				avatarPath = this.getServletContext().getRealPath("boy.png");
			}else{
				avatarPath = this.getServletContext().getRealPath("girl.png");
			}
			logger.info("avatarPath = " + avatarPath);
			User user = new User(id, password, nickname, sex, birthday, avatarPath, userType, new Date());
			//提交用户信息
			UserService us = new UserServiceImpl();
			if (us.registerNewUser(user)) {
				//注册成功
				info.put("register", true);
				info.put("msg", "注册成功！");
			} else {
				//注册失败
				info.put("register", false);
				info.put("msg", "哎呀！注册失败了，请重试！");
			}

		} catch (Exception e) {
			e.printStackTrace();
			//注册失败
			info.put("register", false);
			info.put("msg", "出现异常: " + e);
		}
		responseToBrowser(resp);
	}
}
