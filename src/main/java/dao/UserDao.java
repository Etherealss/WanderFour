package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pojo.po.User;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 用户DAO
 * @date 2020/10/2
 */
public interface UserDao {
	/**
	 * 更新用户头像路径
	 * @param userId
	 * @param filePath
	 */
	void updateUserAvatarPath(@Param("userId") Long userId, @Param("filePath") String filePath);


	/**
	 * 通过账号密码查询用户
	 * @param email
	 * @param password
	 * @return 存在返回true
	 */
	User selectUserBySign(@Param("email") String email, @Param("password") String password);

	/**
	 * 查询用户id是否存在
	 * @param email
	 * @return 存在返回true
	 */
	long countUserByEmail(@Param("email") String email);

	/**
	 * 新建用户
	 * @param user
	 */
	void registerNewUser(User user);

	/**
	 * 修改用户信息
	 * @param user
	 */
	void updateUserInfo(User user);

	/**
	 * 修改用户密码
	 * @param userId
	 * @param password
	 */
	void updateUserPw(@Param("userId") Long userId, @Param("password") String password);

	/**
	 * 查询用户，获取数据
	 * @param email
	 * @return
	 */
	User getUserByEmail(String email);

	/**
	 * 查询用户，获取数据
	 * @param userId
	 * @return
	 */
	User getUserById(Long userId);

	/**
	 * 获取用户邮箱
	 * @param userId
	 * @return
	 */
	User getUserEmailAndPwById(Long userId);

	/**
	 * 根据id获取评论用户的头像和昵称
	 * @param userId
	 * @return
	 */
	User getImgAndNicknameById(Long userId);

	/**
	 * 获取用户列表信息
	 * @param ids
	 * @return
	 */
	List<User> getUsersInfo(@Param("ids") List<Long> ids);
}
