package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "测试接口Test2")
@Controller
@RequestMapping("/test")
public class Test2 {

    @ApiOperation("测试GetMapping")
    @GetMapping(value = "/do")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Test2");
    }
}
