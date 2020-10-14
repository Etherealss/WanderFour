package common.strategy;

import common.enums.TargetType;

/**
 * @author 寒洲
 * @description 点赞的策略
 * @date 2020/10/14
 */
public interface LikeStategy {

	/**
	 * 点赞
	 * @param userid
	 * @param targetId
	 * @param likeState
	 * @param likeType
	 */
	void like(String userid, Long targetId, int likeState, TargetType likeType);

	/**
	 * 取消点赞 / 未点赞
	 * @param userid
	 * @param targetId
	 * @param likeState
	 * @param likeType
	 */
	void unlike(String userid, Long targetId, int likeState, TargetType likeType);

	/**
	 * 踩 / 不喜欢
	 * @param userid
	 * @param targetId
	 * @param likeState
	 * @param likeType
	 */
	void dislike(String userid, Long targetId, int likeState, TargetType likeType);
}
