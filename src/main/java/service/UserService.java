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
	 * 检查账号是否存在
	 * @param email
	 * @return
	 */
	ResultType checkUserExist(String email);

	/**
	 * 验证登录的账号密码
	 * @param email
	 * @param paasword
	 * @return
	 */
	ResultType validateUserLogin(String email, String paasword);

	/**
	 * 注册
	 * @param user
	 * @return
	 */
	ResultType registerNewUser(User user);
}
