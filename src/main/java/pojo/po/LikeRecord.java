package pojo.po;

import common.enums.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author 寒洲
 * @description 用户点赞类
 * @date 2020/10/13
 */
public class LikeRecord implements Serializable {

	private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
	/**
	 * 数据库主键
	 */
	private Long id;
	/**
	 * 点赞的用户账号
	 */
	private Long userid;
	/**
	 * 点赞的目标编号
	 */
	private Long targetId;
	/**
	 * 目标类型 文章/评论/帖子
	 */
	private int targetTypeInt;
	/**
	 * 点赞状态
	 * 1 为 点赞
	 * 0 为 取消点赞或者未点赞
	 */
	private int likeState;

	private TargetType targetType;

	public LikeRecord() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public void setTargetType(int targetTypeInt) {
		logger.debug("targetTypeInt = " + targetTypeInt);
		if (targetType == null) {
			this.targetType = TargetType.getTargetType(targetTypeInt);
		}
	}
	public void setTargetType(String targetTypeStr) {
		logger.debug("targetTypeStr = " + targetTypeStr);
		if (targetType == null) {
			this.targetType = TargetType.getTargetType(targetTypeStr);
		}
	}

	public void setTargetType(TargetType targetType) {
		logger.debug("targetTypeEnum = " + targetType);
		this.targetType = targetType;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public int getLikeState() {
		return likeState;
	}

	public void setLikeState(int likeStatus) {
		this.likeState = likeStatus;
	}

	@Override
	public String toString() {
		return "LikeRecord{" +
				"id=" + id +
				", userid=" + userid +
				", targetId=" + targetId +
				", targetTypeInt=" + targetTypeInt +
				", likeState=" + likeState +
				", targetType=" + targetType +
				'}';
	}

}
