package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.bean.User;
import org.apache.log4j.Logger;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserController extends BaseUserServlet{
	private final Logger logger = Logger.getLogger(UserController.class);

	public void Register(HttpServletRequest req, HttpServletResponse resp){
		logger.info("用户注册");
		//注册结果的map
		Map<String, Object> info = new HashMap<>();
		try {
			//获取请求参数
			String nickname = req.getParameter("register-nickname");
			Long id = Long.valueOf(req.getParameter("register-userid"));
			String password = req.getParameter("register-pw1");
			//将获取的生日字符串转换为日期类型
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").
					parse(req.getParameter("register-birthday"));
			String userType = req.getParameter("userType");
			String sex_str = req.getParameter("register-sex");
			//判断性别，小哥哥为true
			Boolean sex = "male".equals(sex_str);
			//根据性别加载默认头像
			// 封装到user对象中
			// 通过User.getbytes(fis)将InputStream转为byte[]，以储存在数据库中
			String avatarPath;
			if (sex) {
				avatarPath = this.getServletContext().getRealPath("boy.png");
			}else{
				avatarPath = this.getServletContext().getRealPath("girl.png");
			}
			// userid,  password,  nickname,  sex,  birthday,
			//	             avatarPath,  userType,  registerDate
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
		//将map转为JSON
		ObjectMapper mapper = new ObjectMapper();
		//发送给客户端
		try {
			mapper.writeValue(resp.getWriter(), info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
