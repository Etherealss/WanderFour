package service.impl;

import common.factory.ServiceFactory;
import org.junit.Test;
import service.LikeService;

import static org.junit.Assert.*;

public class LikeServiceImplTest {

	LikeService service = ServiceFactory.getLikeService();
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