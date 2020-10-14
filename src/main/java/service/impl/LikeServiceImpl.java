package service.impl;

import common.enums.ResultState;
import common.enums.TargetType;
import common.strategy.LikeStategyChoose;
import common.strategy.impl.LikeStategyImpl;
import org.apache.log4j.Logger;
import pojo.po.LikeRecord;
import service.LikeService;

/**
 * @author 寒洲
 * @description 点赞service
 * @date 2020/10/14
 */
public class LikeServiceImpl implements LikeService {

	private Logger logger = Logger.getLogger(LikeServiceImpl.class);


	@Override
	public ResultState LikeOrUnlike(LikeRecord likeRecord) {
		try {
			if (likeRecord.getTargetTypeEnum() == null) {
				logger.debug("点赞类型为null 异常！");
				throw new Exception("点赞类型为null");
			}
			String userid = likeRecord.getUserid();
			Long targetId = likeRecord.getTargetId();
			int likeState = likeRecord.getLikeState();
			TargetType likeType = likeRecord.getTargetTypeEnum();
			LikeStategyChoose stategyChoose = new LikeStategyChoose(new LikeStategyImpl());
			if (likeRecord.getLikeState()==1){
				stategyChoose.like(userid, targetId, likeState, likeType);
			} else {
				stategyChoose.unlike(userid, targetId, likeState, likeType);
			}


		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}

		return null;
	}
}
