package common.strategy;

import common.enums.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/23
 */
public abstract class LikeStrategy {

	protected Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
	/**
	 * 点赞操作
	 * @param userid
	 * @param targetId
	 * @param likeType
	 */
	public abstract void likeOperate(Long userid, Long targetId, TargetType likeType);

	/**
	 * 获取redis缓存的点赞关系的域名
	 * @param userid
	 * @param targetId
	 * @param targetType
	 * @return 形如"targetType::userid::targetId"
	 */
	protected String getLikeFieldName(Long userid, Long targetId, int targetType) {
		String likeKey = targetType + "::" + userid + "::" + targetId;
		return likeKey;
	}

	/**
	 * 获取redis缓存的点赞数量的域名
	 * @param targetId
	 * @param targetType
	 * @return 形如"targetType::targetId"
	 */
	protected String getLikeFieldName(Long targetId, int targetType) {
		String likeKey = targetType + "::" + targetId;
		return likeKey;
	}
}
