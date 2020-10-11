package filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 寒洲
 * @description 检查登录状态
 * @date 2020/10/2
 */
@WebFilter(urlPatterns = {"/*"})
public class ValidateLoginFilter implements Filter {
	private final Logger logger = Logger.getLogger(ValidateLoginFilter.class);

	/**
	 * 需要拦截并判断登录状态的路径
	 */
	private static final Set<String> CHECK_PATHS = Collections.unmodifiableSet(
			new HashSet<>(Arrays.asList("/manage.html", "/write.html",
					"/UserSettingServlet")));


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//获取资源请求路径
		String uri = req.getRequestURI();
		//判断是否是登录相关的资源
		if (CHECK_PATHS.contains(uri)) {
			Object email = req.getServletContext().getAttribute("email");
			if (email == null) {
				//未登录，跳转到登录页面
				logger.trace("检查用户的登录状态 未登录，拦截");
//				resp.sendRedirect("login.html");
			}else{
				//已登录，放行
				chain.doFilter(req, resp);
				logger.trace("检查用户的登录状态 已登录，放行：" + email);
			}
		}else{
			//不是需要登录后才能才看的页面，直接放行
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig config) throws ServletException {}
	public void destroy() {}

}
