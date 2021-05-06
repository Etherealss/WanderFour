import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class PathTest extends HttpServlet {
	private Logger logger = LoggerFactory.getLogger("testLogger");
	@Test
	public void pathTest(){
		String avatarPath = this.getServletContext().getRealPath("boy.png");
		logger.debug(avatarPath);
	}
}
