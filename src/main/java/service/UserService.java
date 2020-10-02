package service;

import common.bean.User;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public interface UserService {
	/**
	 * 检查账号是否存在
	 * @param userid
	 * @return 存在返回true
	 */
	boolean checkUserExist(Long userid);

	/**
	 * 验证登录的账号密码
	 * @param userid
	 * @param paasword
	 * @return 登录成功返回true
	 */
	boolean validateUserLogin(Long userid, String paasword);

	boolean registerNewUser(User user);
}
