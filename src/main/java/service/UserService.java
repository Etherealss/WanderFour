package service;

import pojo.po.User;
import common.enums.ResultType;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public interface UserService {
    /**
     * 检查邮箱是否已注册
     * @param email
     * @return
     * @throws Exception
     */
    ResultType checkUserExist(String email);

    /**
     * 获取用户邮箱和密码
     * @param id
     * @return
     */
    User getUserEmailAndPw(Long id);

    /**
     * 验证登录的账号密码，获取用户数据
     * @param email
     * @param paasword
     * @return
     */
    User validateUserLogin(String email, String paasword);


    /**
     * 注册
     * @param user
     * @return
     */
    Long registerNewUser(User user);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 获取已登录的用户的信息
     * @param userid
     * @return
     */
    User getUserInfo(Long userid);

    /**
     * 修改密码
     * @param userid
     * @param originalPw
     * @param newPw
     * @return
     */
    ResultType updateUserPw(Long userid, String originalPw, String newPw);


}
