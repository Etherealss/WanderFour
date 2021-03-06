package listener;

import common.enums.AttrEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("用户访问创建session：" +
				se.getSession().getAttribute(AttrEnum.LOGIN_SESSION_NAME));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//用户session连接断开，异常退出
		HttpSession session = se.getSession();
		Object userId = session.getAttribute(AttrEnum.LOGIN_SESSION_NAME);
		logger.info("StateListener 用户session连接断开 / 用户退出 : " + userId);
		session.getServletContext().removeAttribute(String.valueOf(userId));
	}
}
