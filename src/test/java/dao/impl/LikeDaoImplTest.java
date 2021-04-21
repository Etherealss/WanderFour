package dao.impl;

import common.enums.TargetType;
import dao.LikeDao;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.LikeRecord;

import java.sql.SQLException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class LikeDaoImplTest {

	@Autowired
	private LikeDao dao;
	private final Logger logger = Logger.getLogger(LikeDaoImplTest.class);
	private final String likeArticleTableName = "`article_like_record`";


	@Test
	public void insertLikeRecord() throws SQLException {
		LikeRecord record = new LikeRecord();
		record.setLikeState(1);
		record.setTargetId(2L);
		record.setTargetType(1);
		record.setUserid(2L);
		dao.createLikeRecord(likeArticleTableName, record);
	}

//	@Test
//	public void updateLikeRecord() throws SQLException {
//		LikeRecord record = new LikeRecord();
//		record.setLikeState(1);
//		record.setLikeTime(new Date());
//		record.setTargetId(2L);
//		record.setTargetType(1);
//		record.setUserid("123456@qq.com");
//		boolean b = dao.updateLikeRecord(conn, record);
//		logger.debug(b);
//	}

	@Test
	public void deleteLikeRecord() throws SQLException {
		LikeRecord record = new LikeRecord();
		record.setTargetId(2L);
		record.setTargetType(1);
		record.setUserid(2L);
		dao.deleteLikeRecord(likeArticleTableName, record);
	}

	@Test
	public void countLikeRecord() throws SQLException {
		long l = dao.countLikeRecord(likeArticleTableName, 2L);
		logger.debug(l);
	}
}