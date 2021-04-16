package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.TargetType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.WritingDto;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/27
 */
@Controller
public class InitWritingController {
    private Logger logger = Logger.getLogger(InitWritingController.class);
    private WritingService<Posts> postsService;
    private WritingService<Article> articleService;

    @RequestMapping(value = "/InitWritingController", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        JSONObject params = GetParamChoose.getJsonByUrl(req);

        //空参检查
        boolean paramMissing = WebUtil.isParamMissing(resp, params, "初始化作品",
                "partition", "order", "type");
        if (paramMissing) {
            return;
        }

        String partStr = params.getString("partition");
        String order = params.getString("order");
        String type = params.getString("type");

        //未登录则获取为null，不影响
        Long userId = WebUtil.getUserId(req);

        int partition = Integer.parseInt(partStr);
        JSONObject resultJson = new JSONObject();

        try {
            if (TargetType.ARTICLE.val().equals(type)) {
                List<WritingDto<Article>> articleList = articleService.getWritingList(userId, partition, order);
                resultJson.put("writings", articleList);

            } else if (TargetType.POSTS.val().equals(type)) {
                List<WritingDto<Posts>> postsList = postsService.getWritingList(userId, partition, order);
                resultJson.put("writings", postsList);
            }

        } catch (Exception e) {
            logger.error("初始化作品页面异常", e);
        }
        ResponseChoose.respToBrowser(resp, resultJson);
    }

    @Autowired
    public void setPostsService(WritingService<Posts> postsService) {
        this.postsService = postsService;
    }

    @Autowired
    public void setArticleService(WritingService<Article> articleService) {
        this.articleService = articleService;
    }
}
