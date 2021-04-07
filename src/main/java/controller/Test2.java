package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description
 * @date 2021/1/6
 */
@Controller
public class Test2 {

    @RequestMapping(value = "/do", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Test2");
    }
}
