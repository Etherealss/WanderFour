package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.DaoEnum;
import common.enums.TargetType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import common.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.po.Article;
import pojo.po.Posts;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description 在显示文章/问贴时，获取侧边的推荐作品
 * @date 2020/10/29
 */
@Controller
public class WritingBesideController {

    private Logger logger = Logger.getLogger(WritingBesideController.class);
    private final static String TYPE_ARTICLE = TargetType.ARTICLE.val();
    private final static String TYPE_POSTS = TargetType.POSTS.val();

    private WritingService<Posts> postsService;
    private WritingService<Article> articleService;

    @RequestMapping(value = "/WritingBesideServlet", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.trace("在显示文章/问贴时，获取侧边的推荐作品");
        JSONObject params = GetParamChoose.getJsonByUrl(req);
        String type = params.getString("type");
        String partition = params.getString("partition");

        //空参检查
        boolean paramMissing = WebUtil.isParamMissing(resp, params, "获取侧边的推荐作品",
                "type", "partition");
        if (paramMissing) {
            return;
        }

        int part = Integer.parseInt(partition);


        JSONObject resJson = new JSONObject();
        if (TYPE_ARTICLE.equals(type)) {
            //获取文章
            try {
                //只获取文章的标题和id，用Simple方法
                List<Article> timeList = articleService.getSimpleWritingList(part, DaoEnum.ORDER_BY_TIME);
                List<Article> likeList = articleService.getSimpleWritingList(part, DaoEnum.ORDER_BY_LIKE);
                resJson.put("new", timeList);
                resJson.put("hot", likeList);
                logger.trace("获取了侧栏文章");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (TYPE_POSTS.equals(type)) {
            //获取帖子
            try {
                List<Posts> timeList = postsService.getSimpleWritingList(part, DaoEnum.ORDER_BY_TIME);
                List<Posts> likeList = postsService.getSimpleWritingList(part, DaoEnum.ORDER_BY_LIKE);
                resJson.put("new", timeList);
                resJson.put("hot", likeList);
                logger.trace("获取了侧栏问贴");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.error("获取侧栏推荐作品，type参数错误");
            ResponseChoose.respWrongParameterError(resp, "type参数错误");
            return;
        }

        ResponseChoose.respToBrowser(resp, resJson);
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
