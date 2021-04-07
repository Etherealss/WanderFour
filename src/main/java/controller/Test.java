package controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description
 * @date 2020/11/25
 */
public class Test {
    private final Logger logger = Logger.getLogger(Test.class);

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet");
//		System.out.println("forward 前");
//		req.getRequestDispatcher("/Test2").forward(req, resp);
//		System.out.println("forward 后");
        System.out.println("redirect 前");
        resp.sendRedirect("/Test2");
        System.out.println("redirect 后");
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPost");
        String file = req.getParameter("file");
        logger.debug(file);
        String name = req.getParameter("name");
        logger.debug(name);
    }
}
