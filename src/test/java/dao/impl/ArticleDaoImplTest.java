package dao.impl;

import common.enums.DaoEnum;
import common.enums.Partition;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.bo.EsBo;
import pojo.po.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class ArticleDaoImplTest {
	private final Logger logger = Logger.getLogger(UserDaoImplTest.class);
	@Qualifier("articleDao")
	@Autowired
	private WritingDao<Article> dao = null;


	@Test
	public void updateArticle() {
		Article writing = new Article();
		writing.setId(68L);
		writing.setPartition(Partition.LEARNING.val());
		writing.setAuthorId(1L);
		writing.setCategory(1);

		writing.setTitle("我是标题");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");

		writing.setCreateTime(new Date());
		writing.setUpdateTime(new Date());

		try {
			//修改文章
			dao.updateWritingInfo(writing);
		} catch (Exception throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void selectArticleById() throws SQLException {
		Article writing = dao.getWritingById(2L);
		logger.debug(writing);
	}

	@Test
	public void testGetSimpleWriting() throws Exception {
		List<Article> simpleWritingListByOrder = dao.getSimpleWritingListByOrder(1, DaoEnum.FIELD_ORDER_BY_LIKE,
				DaoEnum.START_FROM_ZERO, 6);
		for (Article article : simpleWritingListByOrder) {
			logger.debug(article);
		}
	}

	@Test
	public void countArticle() {
	}

	@Test
	public void getBlogListByPage() {
	}

	@Test
	public void getUserArticleCount() {
	}

	@Test
	public void selectLikeCount() throws SQLException {
		int count = dao.getLikeCount(1L);
		logger.debug(count);
	}

	@Test
	public void updateLikeCount() throws SQLException {
		dao.updateLikeCount(2L, 6);
	}


	@Test
	public void testGetByTime() throws Exception {
		List<Article> writingListByLike = dao.getWritingListByOrder(
				1, DaoEnum.FIELD_ORDER_BY_TIME, 0L, 5);
		for (Article article : writingListByLike) {
			logger.debug(article);
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
}