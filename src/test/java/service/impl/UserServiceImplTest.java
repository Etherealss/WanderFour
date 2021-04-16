package service.impl;

import common.enums.ResultType;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.User;
import service.UserService;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class UserServiceImplTest {

	private Logger logger = Logger.getLogger(UserServiceImplTest.class);

	@Autowired
	UserService service;

	@Test
	public void testCheckUserExist() throws Exception {
		ResultType resultType = service.checkUserExist("123456@qq.com");
		logger.debug(resultType);
	}

	@Test
	public void testGetUserEmailAndPw() throws Exception {
		User resultType = service.getUserEmailAndPw(1L);
		logger.debug(resultType);
	}

	@Test
	public void testValidateUserLogin() throws Exception {
		User user = service.validateUserLogin("111111@qq.com", "mima");
		logger.debug(user);
	}

	@Test
	public void testRegisterNewUser() throws Exception {
		User user = new User();
		user.setEmail("123123@test.com");
		user.setPassword("123123");
		user.setNickname("testMyBatis");
		user.setSex(true);
		user.setRegisterDate(new Date());
		user.setBirthday(new Date());
		user.setUserType("SENIOR");
		user.setAvatarPath("D:\\WanderFourAvatar\\default\\girl.png");
		service.registerNewUser(user);
	}

	@Test
	public void testUpdateUserInfo() throws Exception {
		User userInfo = service.getUserInfo(18L);
		userInfo.setBirthday(new Date());
		logger.debug(userInfo);
		userInfo.setNickname("3333");
		service.updateUserInfo(userInfo);
	}

	@Test
	public void testGetUserInfo() throws Exception {
		User userInfo = service.getUserInfo(17L);
		logger.debug(userInfo);
	}

	@Test
	public void testUpdateUserPw() throws Exception {
		ResultType resultType = service.updateUserPw(1L, "mima2", "mima");
		logger.debug(resultType);
	}

	@Test
	public void testTestCheckUserExist() throws Exception {
		ResultType resultType = service.checkUserExist("123123@qq.com");
		logger.debug(resultType);
	}

}