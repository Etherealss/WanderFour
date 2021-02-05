package service;

import pojo.po.User;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/21
 */
public interface FriendRelationService {

	/**
	 * 获取好友列表信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<User> getFriendsInfo(Long userId) throws Exception;
}
