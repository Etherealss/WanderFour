package controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import common.enums.AttrEnum;
import common.util.WebUtil;
import common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.dto.ResultState;
import common.strategy.choose.GetParamChoose;
import common.strategy.choose.ResponseChoose;
import pojo.po.User;
import common.enums.ResultType;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 寒洲
 * @description 登录注册操作
 * @date 2020/10/2
 */
@Controller
public class UserEnterController {
    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    private final static String ACTION_LOGIN = "login";
    private final static String ACTION_REGISTER = "register";

    private UserService userService;

    @RequestMapping(value = "/UserEnterServlet", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject params = GetParamChoose.getJsonByJson(req);
        String action = params.getString("action");
        try {
            if (ACTION_LOGIN.equals(action)) {
                login(req, resp, params);
            } else if (ACTION_REGISTER.equals(action)) {
                register(resp, params);
            } else {
                ResponseChoose.respOnlyStateToBrowser(resp, ResultType.EXCEPTION, "错误的方法，检查action参数：action=login(/register)");
                throw new ServletException("错误的方法：action = " + action);
            }
        } catch (Exception e) {
            logger.error("登录注册异常", e);
        }
    }

    public void login(HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws ServletException {
        logger.trace("用户登录...");

        //空参检查
        boolean paramMissing = WebUtil.isParamMissing(resp, params, "登录",
                "email", "pw");
        if (paramMissing) {
            return;
        }

        String email = params.getString("email");
        String password = params.getString("pw");
        //密码再次加密
        logger.debug(email + ", " + password);
        password = Md5Utils.md5Encode((email + password));
        logger.debug("登录密码" + password);

        ResultType state;
        JSONObject jsonObject = new JSONObject();
        //默认为异常
        ResultState info = new ResultState(ResultType.EXCEPTION, "登录异常");
        //执行操作，获取结果
        state = userService.checkUserExist(email);
        //用户存在
        if (state == ResultType.IS_REGISTED) {

            User user = userService.validateUserLogin(email, password);
            if (user != null) {
                //密码正确，检查异地登录
                logger.trace("密码正确，检查登录");
                HttpSession session = req.getSession();
                String sessionId = session.getId();

                String useridStr = String.valueOf(user.getId());
                //通过ServletContext检查异地登录
                ServletContext servletContext = session.getServletContext();
                if (servletContext.getAttribute(useridStr) != null &&
                        !StringUtils.equals(sessionId, servletContext.getAttribute(useridStr).toString())) {
                    //如果servletContext中存在email，说明已登录，比较两个sessionId
                    //如果两个sessionId相同，说明是同个地点登录
                    //输出“您已登录”
                    logger.trace("已经登录了");
                    state = ResultType.LOGGED;
                    info = new ResultState(state, "已登录");
                } else {
                    //当前用户未登录
                    state = ResultType.SUCCESS;
                    //返回前端
                    jsonObject.put("user", user);

                    if (servletContext.getAttribute(useridStr) == null) {
                        //未登录，用email做标识，存入请求的sessionId
                        logger.trace("未登录，好了，现在登录了");
                        info = new ResultState(state, "未登录，好了，现在登录了");
                    } else {
                        //如果两个sessionId 不相同，说明是已在别处登录，踢下异地用户
                        logger.trace("异地登录！");
                        servletContext.removeAttribute(useridStr);
                        info = new ResultState(state, "已异地登录！踢下异地用户");
                    }
                    //用servletContext判断登录状态，用session储存用户的信息
                    servletContext.setAttribute(useridStr, sessionId);
                    session.setAttribute(AttrEnum.LOGIN_SESSION_NAME, user.getId());

                    logger.info("用户：" + user.getId() + "登录");
                }
            } else {
                //密码错误
                state = ResultType.PW_ERROR;
                info = new ResultState(state, "密码错误");
            }
        } else if (state == ResultType.USER_UN_FOUND) {
            info = new ResultState(state, "用户不存在");
        }

        jsonObject.put("state", info);
        ResponseChoose.respToBrowser(resp, jsonObject);
    }

    public void register(HttpServletResponse resp, JSONObject params) throws ServletException {
        logger.trace("用户注册...");
        //获取请求参数，封装到实体中
        User user = GetParamChoose.getObjByParam(params, User.class);
        if (user == null) {
            ResponseChoose.respNoParameterError(resp, "注册时获取不到user对象");
            return;
        }
        //密码再次加密
        logger.debug("未加密：" + user);
        user.setPassword(Md5Utils.md5Encode((user.getEmail() + user.getPassword())));
        logger.debug("已加密：" + user);

        //提交用户信息，获取业务结果
        Long userid = null;
        try {
            userid = userService.registerNewUser(user);
            Objects.requireNonNull(userid, "用户注册异常，userId为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //注册结果返回浏览器
        respRegisterResult(resp, userid);
    }

    /**
     * 注册结果返回浏览器
     * @param resp
     * @param userid
     */
    private void respRegisterResult(HttpServletResponse resp, Long userid) {
        logger.debug(String.valueOf(userid));
        ResultType resultType = userid == null ? ResultType.EXCEPTION : ResultType.SUCCESS;
        ResultState state = new ResultState(resultType, "注册结果，包含userid");
        JSONObject json = new JSONObject();
        json.put("state", state);
        json.put("userid", userid);
        ResponseChoose.respToBrowser(resp, json);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
