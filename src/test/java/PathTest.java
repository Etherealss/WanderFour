import org.junit.Test;

import javax.servlet.http.HttpServlet;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class PathTest extends HttpServlet {
	@Test
	public void pathTest(){
		String avatarPath = this.getServletContext().getRealPath("boy.png");
		System.out.println(avatarPath);
	}
}
