package dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/21
 */
public interface FriendRelationDao {

	/**
	 * 获取好友列表Id
	 * @return
	 * @param userId
	 */
	List<Long> getFriendsId(Long userId);
}
