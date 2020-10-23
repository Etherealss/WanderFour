package common.strategy.choose;

import common.enums.TargetType;
import common.strategy.LikeStrategy;

/**
 * @author 寒洲
 * @description 点赞策略选择
 * @date 2020/10/14
 */
public class LikeStategyChoose {
	private LikeStrategy likeStrategy;

	public LikeStategyChoose(LikeStrategy likeStrategy){
		this.likeStrategy = likeStrategy;
	}

	/**
	 * 点赞相关操作
	 * @param userid 点赞的用户
	 * @param targetId 被点赞的目标
	 * @param likeType 被点赞的目标类型 文章/帖子/评论
	 */
	public void likeOperator(Long userid, Long targetId, TargetType likeType) {
		likeStrategy.likeOperate(userid, targetId, likeType);
	}
}
