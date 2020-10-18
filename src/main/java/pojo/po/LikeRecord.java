package pojo.po;

import common.enums.TargetType;

import java.util.Date;

/**
 * @author 寒洲
 * @description 用户点赞类
 * @date 2020/10/13
 */
public class LikeRecord {

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
	private int targetType;
	/**
	 * 点赞状态
	 * 1 为 点赞
	 * 0 为 取消点赞或者未点赞
	 */
	private int likeState;

	private TargetType targetTypeEnum;

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
		if (targetTypeEnum == null){
			this.targetTypeEnum = TargetType.getTargetType(targetType);
		}
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public int getLikeState() {
		return likeState;
	}

	public void setLikeState(int likeStatus) {
		this.likeState = likeStatus;
	}

	public TargetType getTargetTypeEnum() {
		return targetTypeEnum;
	}

	public void setTargetTypeEnum(TargetType targetTypeEnum) {
		this.targetTypeEnum = targetTypeEnum;
		this.targetType = targetTypeEnum.code();
	}
}
