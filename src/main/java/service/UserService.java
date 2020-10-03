package service;

import common.bean.User;

import java.sql.SQLException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public interface UserService {
	/**
	 * 检查账号是否存在
	 * @param userid
	 * @return 存在返回true，不存在为false
	 */
	boolean checkUserExist(Long userid);

	/**
	 * 验证登录的账号密码
	 * @param userid
	 * @param paasword
	 * @return 登录成功-200，账号不存在-404，异地登录-401，密码错误-400，异常-500
	 */
	int validateUserLogin(Long userid, String paasword);

	boolean registerNewUser(User user);
}
