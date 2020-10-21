package common.strategy.impl;

import common.strategy.LikeStategy;
import common.strategy.choose.LikeStategyChoose;
import org.junit.Test;
import pojo.po.LikeRecord;

import static org.junit.Assert.*;

public class LikeStategyImplTest {

	private LikeRecord getDufaultLikeRecord(){
		LikeRecord record = new LikeRecord();
		record.setTargetType("article");
		record.setUserid(99L);
		record.setTargetId(3L);
		return record;
	}
	@Test
	public void like() {
		LikeRecord re = getDufaultLikeRecord();
		LikeStategy stategy = new LikeStategyImpl();
		stategy.like(re.getUserid(),re.getTargetId(), 1, re.getTargetType());
	}

	@Test
	public void likeServalTimes() {
		LikeStategy stategy = new LikeStategyImpl();
		LikeRecord re = new LikeRecord();
		re.setTargetType("article");
		for (int i = 1; i <= 3; i++) {
			re.setUserid((long) i);
			re.setTargetId((long) i);
			stategy.like(re.getUserid(),re.getTargetId(), 1, re.getTargetType());
		}
	}

	@Test
	public void unlike() {
		LikeRecord re = getDufaultLikeRecord();
		LikeStategy stategy = new LikeStategyImpl();
		stategy.unlike(re.getUserid(),re.getTargetId(), 0, re.getTargetType());
	}
}