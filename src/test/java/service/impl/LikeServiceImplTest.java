package service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.LikeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class LikeServiceImplTest {

	@Autowired
	LikeService service;
	@Test
	public void likeOrUnlike() {
	}

	@Test
	public void persistLikeRecord() throws Exception {
		service.persistLikeRecord();
	}

	@Test
	public void persistLikeCount() throws Exception {
		service.persistLikeCount();
	}


}