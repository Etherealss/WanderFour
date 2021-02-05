package service.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.FriendRelationDao;
import dao.UserDao;
import pojo.po.User;
import service.FriendRelationService;

import java.sql.Connection;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/21
 */
public class FriendRelationServiceImpl implements FriendRelationService {
	@Override
	public List<User> getFriendsInfo(Long userId) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		FriendRelationDao dao = DaoFactory.getFriendRelationDao();
		List<Long> friendsId = dao.getFriendsId(conn, userId);
		UserDao userDao = DaoFactory.getUserDAO();
		return userDao.getUsersInfo(conn, friendsId);
	}
}
