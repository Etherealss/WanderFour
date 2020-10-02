package filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 检查登录状态
 * @date 2020/10/2
 */
@WebFilter(urlPatterns = {"/*"})
public class ValidateLoginFilter implements Filter {
	private final Logger logger = Logger.getLogger(ValidateLoginFilter.class);
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
