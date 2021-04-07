package dao.impl;

import common.enums.DaoEnum;
import common.util.TestUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.bo.EsBo;
import pojo.po.Posts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class PostsDaoImplTest {

	private final Logger logger = Logger.getLogger(PostsDaoImplTest.class);

	@Autowired
	@Qualifier("postsDao")
	private WritingDao<Posts> dao = null;

	@Test
	public void updateNewWritingInfo() throws SQLException {
		Posts posts = TestUtil.getDefaultPostsPo();
		Long maxId  = dao.createWritingInfo(posts);
		logger.debug("发表新帖子：" + maxId);
	}


	@Test
	public void updateWritingInfo() throws SQLException {
		Posts posts = TestUtil.getDefaultPostsPo();
		posts.setId(2L);
		posts.setContent("修改内容");
		dao.updateWritingInfo(posts);

	}

	@Test
	public void selectWritingById() throws SQLException {
		Posts posts = dao.getWritingById(2L);
		int collected = posts.getCollected();
		logger.debug(posts);
		logger.debug(collected);
	}

	@Test
	public void deleteWritingById() throws SQLException {
		dao.deleteWritingById(34L, 4L);
	}

	@Test
	public void countWriting() {
		Long aLong = dao.countWriting(1);
		logger.debug(aLong);
	}

	@Test
	public void getWritingListByPage() {
		List<Posts> like = dao.getWritingListByOrder(1, "like", 0L, 5);
		logger.debug(like);
	}

	@Test
	public void getUserWritingCount() {
		long userWritingCount = dao.getUserWritingCount(4L);
		logger.debug(userWritingCount);
	}

	@Test
	public void getAuthorByWritingId() throws SQLException {
		Long authorByWritingId = dao.getAuthorByWritingId(2L);
		logger.debug("问贴作者：" + authorByWritingId);
	}

	@Test
	public void testGetByTime() throws Exception {
		List<Posts> writingListByTime = dao.getWritingListByOrder(
				1, "`create_time`", 0L, 5);
		for (Posts posts : writingListByTime) {
			logger.debug(posts);
		}
	}

	@Test
	public void testGetWritingsByIds() throws Exception {
		List<Long> list = new ArrayList<>();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		List<EsBo> writingsByIds = dao.getWritingsByIds(list);
		for (EsBo writingsById : writingsByIds) {
			logger.debug(writingsById.toString());
		}
	}

	@Test
	public void testGetSimpleWritingListByOrder() throws Exception {
		List<Posts> simpleWritingListByOrder = dao.getSimpleWritingListByOrder(1, DaoEnum.FIELD_ORDER_BY_TIME, 0L, 5);
		for (Posts p : simpleWritingListByOrder) {
			logger.debug(p);
		}
	}
}