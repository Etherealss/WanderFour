package dao.impl;

import common.enums.ApplicationConfig;
import common.enums.UserType;
import dao.UserDao;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pojo.po.User;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class UserDaoImplTest {
	private Logger logger = LoggerFactory.getLogger("testLogger");
	@Autowired
	private UserDao dao;

	@Test
	public void testSelectUserByPw() throws Exception {
		String email = "123456@qq.com";
		String pw = "2i8jdhgfnouflho1iqsl47tc4l";
		User user = dao.selectUserBySign(email, pw);
		logger.debug("selectUserByPw()测试结果：" + user);
	}

	@Test
	public void testSelectUserById() throws Exception {
		Long email = 1L;
		User b = dao.getUserById(email);
		logger.debug(b.toString());
	}

	@Test
	public void testUpdateNewUser() throws SQLException {
		User user = new User("12333@test.com", "123123", "李四", true, "null", UserType.TEACHER, new Date());
		dao.registerNewUser(user);
	}

	@Test
	public void testSelectUserByEmail() throws SQLException {
		String emial = "123456@qq.com";
		User user = dao.getUserByEmail(emial);
		logger.debug(user.toString());
	}

	@Test
	public void testPw() throws Exception {
		Long userId = 4L;
		// 2i8jdhgfnouflho1iqsl47tc4l
		String pw = "2i8jdhgfnouflho1iqsl47tc4l";
		dao.updateUserPw(userId, pw);
	}

	@Test
	public void testUpdateUserAvatarPath() throws Exception {
		Long userId = 4L;
		String filePath = ApplicationConfig.AVATAR_DIR + userId + ".png";
		logger.debug(filePath);
		dao.updateUserAvatarPath(userId, filePath);
	}

	@Test
	public void testGetUsers() throws Exception {
		List<Long> list = new ArrayList<>();
		list.add(1L);
		list.add(2L);
		List<User> usersInfo = dao.getUsersInfo(list);
		if (usersInfo == null) {
			logger.debug("失败");
			return;
		}
		for (User user : usersInfo) {
			logger.debug(user.toString());
		}
	}

	@Test
	public void testAvatar() throws Exception {
		String a = ApplicationConfig.AVATAR_DEFAULT_PATH_GIRL;
		System.out.println(a);
	}
}