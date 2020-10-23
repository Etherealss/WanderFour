package common.strategy.impl;

import common.strategy.LikeStrategy;
import org.junit.Test;
import pojo.po.LikeRecord;

public class LikeStrategyImplTest {

	private LikeRecord getDufaultLikeRecord(){
		LikeRecord record = new LikeRecord();
		record.setTargetType("article");
		record.setUserid(1L);
		record.setTargetId(1L);
		return record;
	}
	@Test
	public void like() {
		LikeRecord re = getDufaultLikeRecord();
		LikeStrategy stategy = new LikeStrategyImpl();
		stategy.likeOperate(re.getUserid(),re.getTargetId(), re.getTargetType());
	}

	@Test
	public void likeServalTimes() {
		LikeStrategy stategy = new LikeStrategyImpl();
		LikeRecord re = new LikeRecord();
		re.setTargetType("article");
		for (int i = 1; i <= 3; i++) {
			re.setUserid((long) i);
			re.setTargetId((long) i);
			stategy.likeOperate(re.getUserid(),re.getTargetId(), re.getTargetType());
		}
	}

	@Test
	public void cancelLike() {
		LikeRecord re = getDufaultLikeRecord();
		LikeStrategy stategy = new CancelLikeStrategyImpl();
		stategy.likeOperate(re.getUserid(),re.getTargetId(), re.getTargetType());
	}
}