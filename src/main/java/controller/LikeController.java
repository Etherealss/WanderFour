package controller;

import common.enums.ResultType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.po.LikeRecord;
import service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 点赞
 * @date 2020/10/13
 */
@Controller
public class LikeController {

    private final Logger logger = Logger.getLogger(LikeController.class);
    private LikeService likeService;

    @RequestMapping(value = "/LikeServlet", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.trace("点赞");
        LikeRecord record = GetParamChoose.getObjByJson(req, LikeRecord.class);
        //空参检查
        if (record == null) {
            ResponseChoose.respNoParameterError(resp, "点赞");
            return;
        }

        Long userId = WebUtil.getUserId(req);
        if (userId == null) {
            logger.error("点赞时用户未登录");
            ResponseChoose.respUserUnloggedError(resp);
            return;
        }
        record.setUserid(userId);
        logger.debug(record.toString());

        //点赞
        ResultType resultType = likeService.likeOrUnlike(record);
        //返回结果
        ResponseChoose.respOnlyStateToBrowser(resp, resultType, "点赞操作");
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }
}
