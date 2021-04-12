package controller;

import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.enums.TargetType;
import common.util.WebUtil;
import common.util.SecurityUtil;
import common.util.SensitiveUtil;
import filter.SensitiveFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.ResultState;
import common.enums.ResultType;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import pojo.bean.WritingBean;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;
import service.WritingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 寒洲
 * @description 作品（文章和帖子）
 * @date 2020/10/7
 */
@Controller
public class WritingController {

    private Logger logger = Logger.getLogger(WritingController.class);

    private final static String TYPE_ARTICLE = TargetType.ARTICLE.val();
    private final static String TYPE_POSTS = TargetType.POSTS.val();

    private WritingService<Posts> postsService;
    private WritingService<Article> articleService;

    @RequestMapping(value = "/WritingServlet", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //获取参数
        JSONObject params = GetParamChoose.getJsonByUrl(req);
        Long userId = WebUtil.getUserId(req);
        if (params == null) {
            //空参为异常，需要有参数才能获取指定数据
            ResponseChoose.respNoParameterError(resp, "查询作品");
            return;
        } else if (AttrEnum.UNDEFINED.equals(params.getString(TYPE_ARTICLE))) {
            ResponseChoose.respWrongParameterError(resp, "参数undefined");
            return;
        } else if (AttrEnum.UNDEFINED.equals(params.getString(TYPE_POSTS))) {
            ResponseChoose.respWrongParameterError(resp, "参数undefined");
            return;
        }
        logger.trace("获取作品 params = " + params);

        //确认作品类型，如果是文章，url为?article=xxx，可以获取article参数
        boolean isArticle = (params.get(TYPE_ARTICLE) != null);
        //根据类型获取实体和Service
        if (isArticle) {
            WritingBean<Article> article = null;
            try {
                article = articleService.getWritingBean(
                        Long.valueOf(String.valueOf(params.get(TYPE_ARTICLE))), WebUtil.getUserId(req));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //判空并返回客户端
            checkResultAndResp(resp, article);

        } else if (params.get(TYPE_POSTS) != null) {
            //获取问贴
            WritingBean<Posts> posts = null;
            try {
                posts = postsService.getWritingBean(
                        Long.valueOf(String.valueOf(params.get(TYPE_POSTS))), WebUtil.getUserId(req));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //判空并返回客户端
            checkResultAndResp(resp, posts);
        } else {
            logger.error("获取作品空参");
            ResponseChoose.respNoParameterError(resp, "获取作品");
        }
    }

    /**
     * 判空并返回客户端
     * @param writing
     * @param resp
     * @throws ServletException
     */
    private void checkResultAndResp(HttpServletResponse resp, WritingBean<? extends Writing> writing) throws ServletException {
        JSONObject jsonObject = new JSONObject();
        ResultState state;

        if (writing == null) {
            // 查不到文章 跳转到404
            state = new ResultState(ResultType.NO_RECORD, "参数错误，查询不到作品");
            jsonObject.put("state", state);
            ResponseChoose.respUnFoundError(resp, "查询不到作品");
        } else {
            //查询到，传给前端
            jsonObject.put("writingBean", writing);
            state = new ResultState(ResultType.SUCCESS, "查询结果");
            jsonObject.put("state", state);
            ResponseChoose.respToBrowser(resp, jsonObject);
        }
    }

    @RequestMapping(value = "/WritingServlet", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.trace("发布作品");
        JSONObject params = GetParamChoose.getJsonByJson(req);
        if (params == null) {
            ResponseChoose.respException(resp, "发表作品");
            return;
        }
        String type = params.getString("type");

        // 空参检查
        if ("".equals(type) || type == null) {
            logger.error("发表作品时缺少参数type");
            ResponseChoose.respNoParameterError(resp, "发表作品");
            return;
        }
//		Long userId = 4L;
        Long userId = WebUtil.getUserId(req);
        if (userId == null) {
            logger.error("发表作品时用户未登录");
            ResponseChoose.respUserUnloggedError(resp);
            return;
        }

        //因为属性都在writing里，无法通过子类型反射属性获取实例，只有通过父类型获取
        Writing writing = GetParamChoose.getObjByParam(params, Writing.class);
        if (writing == null) {
            logger.error("发表作品空参");
            ResponseChoose.respNoParameterError(resp, "发表作品");
            return;
        }

        //确定类型
        //根据类型获取实体和Service
        if (TYPE_ARTICLE.equals(type)) {
            Article article = writing.toOthers(new Article());
            logger.debug(article);

            article.setAuthorId(userId);
            //发表新文章
            Long articleId = null;
            //过滤敏感词
            SensitiveUtil.filterWriting(new SensitiveFilter(), article);
            //html防注入
//			SecurityUtil.ensureJsSafe(article);
            SecurityUtil.htmlEncode(article);
            try {
                articleId = articleService.publishNewWriting(article);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //判空并返回客户端
            checkResultAndResp(resp, articleId);

        } else if (TYPE_POSTS.equals(type)) {
            //获取参数
            Posts posts = writing.toOthers(new Posts());
            logger.debug(posts);

            posts.setAuthorId(userId);
            //发表新文章
            Long postsId = null;
            //过滤敏感词
            SensitiveUtil.filterWriting(new SensitiveFilter(), posts);
            //html防注入
//			SecurityUtil.ensureJsSafe(posts);
            SecurityUtil.htmlEncode(posts);
            try {
                postsId = postsService.publishNewWriting(posts);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //判空并返回客户端
            checkResultAndResp(resp, postsId);
        } else {
            logger.error("doPost参数错误type =" + type);
            ResponseChoose.respWrongParameterError(resp, "发表作品的type参数");
        }
    }

    /**
     * 判空并返回客户端
     * @param writingId
     * @param resp
     * @throws ServletException
     */
    private void checkResultAndResp(HttpServletResponse resp, Long writingId) {
        JSONObject json = new JSONObject();
        if (writingId != null) {
            json.put("writingId", writingId);
            json.put("state", new ResultState(ResultType.SUCCESS, "发表成功"));
            ResponseChoose.respToBrowser(resp, json);
        } else {
            ResponseChoose.respException(resp, "出现异常，发表失败");
        }
    }

    @RequestMapping(value = "/WritingServlet", method = RequestMethod.PUT)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        JSONObject param = GetParamChoose.getJsonByJson(req);
        logger.trace(param);
        //空参检查
        if (param == null) {
            ResponseChoose.respNoParameterError(resp, "修改作品");
            return;
        }
        Long userId = WebUtil.getUserId(req);
        if (userId == null) {
            logger.error("修改作品时用户未登录");
            ResponseChoose.respUserUnloggedError(resp);
            return;
        }
        param.put("authorId", userId);
        String type = param.getString("type");
        logger.trace("修改作品 type = " + type);

        //空参检查
        if ("".equals(type) || type == null) {
            ResponseChoose.respNoParameterError(resp, "修改作品(type参数)");
            return;
        }

        //根据类型获取实体和Service
        Writing writing = GetParamChoose.getObjByParam(param, Writing.class);

        //空参检查
        if (writing == null) {
            ResponseChoose.respNoParameterError(resp, "修改作品时获取不到信息");
            return;
        }

        if (TYPE_ARTICLE.equals(type)) {
            Article article = writing.toOthers(new Article());
            logger.debug("获取article参数：\n\t" + article);

            //修改文章
            ResultType resultType;
            //过滤敏感词
            SensitiveUtil.filterWriting(new SensitiveFilter(), article);
            //html防注入
//			SecurityUtil.ensureJsSafe(article);
            SecurityUtil.htmlEncode(article);
            try {
                resultType = articleService.updateWriting(article);
                ResponseChoose.respOnlyStateToBrowser(resp, resultType, "文章修改结果");
            } catch (Exception e) {
                e.printStackTrace();
                ResponseChoose.respException(resp, "修改文章");
            }

        } else if (TYPE_POSTS.equals(type)) {
            logger.trace("Posts put:" + type);

            Posts posts = writing.toOthers(new Posts());
            logger.debug("获取article参数：\n\t" + posts);

            //修改文章
            ResultType resultType;

            //过滤敏感词
            SensitiveUtil.filterWriting(new SensitiveFilter(), posts);
            //html防注入
//			SecurityUtil.ensureJsSafe(posts);
            SecurityUtil.htmlEncode(posts);
            try {
                resultType = postsService.updateWriting(posts);
            } catch (Exception e) {
                e.printStackTrace();
                resultType = ResultType.EXCEPTION;
            }

            ResponseChoose.respOnlyStateToBrowser(resp, resultType, "问贴修改结果");
        } else {
            //空参
            logger.error("doPut空参");
            ResponseChoose.respNoParameterError(resp, "修改作品(type参数)");
            return;
        }
    }

    @RequestMapping(value = "/WritingServlet", method = RequestMethod.DELETE)
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        JSONObject param = GetParamChoose.getJsonByJson(req);
        logger.trace("删除作品" + param);
        //空参检查
        if (param == null) {
            ResponseChoose.respNoParameterError(resp, "修改作品");
            return;
        }

        Long userId = WebUtil.getUserId(req);
        if (userId == null) {
            logger.error("删除作品时用户未登录");
            ResponseChoose.respUserUnloggedError(resp);
            return;
        }

        Long deleteId = param.getLong(TYPE_ARTICLE);
        WritingService<?> service;
        if (deleteId != null) {
            //删除文章
            service = articleService;
        } else if (param.getLong(TYPE_POSTS) != null) {
            //删除问贴
            deleteId = param.getLong(TYPE_POSTS);
            service = postsService;
        } else {
            //空参
            ResponseChoose.respNoParameterError(resp, "删除作品(type参数)");
            return;
        }

        // 删除、获取结果
        ResultType resultType;
        try {
            // userId即删除者的id，要用来判断是否是评论作者
            resultType = service.deleteWriting(deleteId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            resultType = ResultType.EXCEPTION;
        }
        ResponseChoose.respOnlyStateToBrowser(resp, resultType, "文章删除结果");
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
