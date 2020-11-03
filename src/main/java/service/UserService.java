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
	ResultType checkUserExist(String email) throws Exception;

	/**
	 * 获取用户邮箱和密码
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User getUserEmailAndPw(Long id) throws Exception;
	/**
	 * 验证登录的账号密码，获取用户数据
	 * @param email
	 * @param paasword
	 * @return
	 * @throws Exception
	 */
	User validateUserLogin(String email, String paasword) throws Exception;


	/**
	 * 注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Long registerNewUser(User user) throws Exception;

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean updateUserInfo(User user) throws Exception;

	/**
	 * 获取已登录的用户的信息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	User getLoggedUserInfo(Long userid) throws Exception;

	/**
	 * 修改密码
	 * @param userid
	 * @param orginal
	 * @param newPw
	 * @return
	 * @throws Exception
	 */
	ResultType updateUserPw(Long userid, String orginal, String newPw) throws Exception;
}
