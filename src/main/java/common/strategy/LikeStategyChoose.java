package common.strategy;

import common.enums.TargetType;
import common.strategy.impl.LikeStategyImpl;

/**
 * @author 寒洲
 * @description 点赞策略选择
 * @date 2020/10/14
 */
public class LikeStategyChoose {
	private LikeStategy likeStategy;

	public LikeStategyChoose(LikeStategy likeStategy){
		this.likeStategy = likeStategy;
	}

	/**
	 * 点赞
	 * @param userid 点赞的用户
	 * @param targetId 被点赞的目标
	 * @param likeState 点赞状态 1-点赞 0-未点赞/取消点赞
	 * @param likeType 被点赞的目标类型 文章/帖子/评论
	 */
	public void like(String userid, Long targetId, int likeState, TargetType likeType) {
		//...
		likeStategy.like(userid, targetId, likeState, likeType);
	}

	/**
	 * 取消点赞
	 * @param userid
	 * @param targetId
	 * @param likeState
	 * @param likeType
	 */
	public void unlike(String userid, Long targetId, int likeState, TargetType likeType) {
		likeStategy.unlike(userid, targetId, likeState, likeType);
	}

}
