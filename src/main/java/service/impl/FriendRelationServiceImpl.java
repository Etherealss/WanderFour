package service.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.FriendRelationDao;
import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	@Autowired
	private FriendRelationDao dao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getFriendsInfo(Long userId) {
		List<Long> friendsId = dao.getFriendsId(userId);
		return userDao.getUsersInfo(friendsId);
	}
}
