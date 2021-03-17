package service.impl;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.User;
import service.FriendRelationService;
import service.UserService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class FriendRelationServiceImplTest {

	private Logger logger = Logger.getLogger(FriendRelationServiceImplTest.class);

	@Autowired
	private FriendRelationService service;

	@Test
	public void testGetFriendsInfo() throws Exception {
		List<User> friendsInfo = service.getFriendsInfo(1L);
		for (User user : friendsInfo) {
			logger.debug(user);
		}
	}
}