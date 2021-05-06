package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		//转换编码后放行
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
