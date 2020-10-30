package listener;

import org.apache.log4j.Logger;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author 寒洲
 * @description 判断用户在线状况的监听器
 * @date 2020/8/21
 */
@WebListener
public class UserStateListener implements HttpSessionListener {

	private Logger logger = Logger.getLogger(UserStateListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("用户访问创建session");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//用户session连接断开，异常退出
		HttpSession session = se.getSession();
		Object userid = session.getAttribute("userid");
		logger.info("StateListener 用户session连接断开 / 用户退出 : " + userid);
	}
}
