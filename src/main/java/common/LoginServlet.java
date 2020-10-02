package common;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private final Logger logger = Logger.getLogger(LoginServlet.class);

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("LoginServlet...");
		System.out.println(req.getParameter("emailInt"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
