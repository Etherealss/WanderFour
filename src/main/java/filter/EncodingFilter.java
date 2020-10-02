package filter;

import controller.BaseUserServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 控制和转换编码格式
 * @date 2020/10/2
 */
@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
	private final Logger logger = Logger.getLogger(EncodingFilter.class);
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		//转换编码后放行
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
