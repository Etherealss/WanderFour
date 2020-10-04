package service;

import common.bean.User;
import common.emun.ResultState;

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
	ResultState checkUserExist(String email);

	/**
	 * 验证登录的账号密码
	 * @param email
	 * @param paasword
	 * @return
	 */
	ResultState validateUserLogin(String email, String paasword);

	/**
	 * 注册
	 * @param user
	 * @return
	 */
	ResultState registerNewUser(User user);
}
