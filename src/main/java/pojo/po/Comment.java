package pojo.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author 寒洲
 * @description 评论和回复
 * @date 2020/10/22
 */
public class Comment {
	@JSONField(ordinal = 0)
	private Long id;

	@JSONField(ordinal = 1)
	private Long userid;

	/**
	 * 记录悬挂的父对象Id，
	 * 如评论悬挂在文章/帖子之下
	 * 回复悬挂在评论之下
	 * 而回复的子回复同样悬挂在评论之下
	 */
	@JSONField(ordinal = 3)
	private Long parentId;

	/**
	 * 评论/回复的目标Id
	 */
	@JSONField(ordinal = 4)
	private Long targetId;

	/**
	 * 状态，未删除为1（默认），已删除为0
	 */
	@JSONField(ordinal = 5)
	private boolean state;

	@JSONField(ordinal = 6)
	private String content;

	/**
	 * 获赞数
	 */
	@JSONField(ordinal = 7)
	private int liked;

	@JSONField(ordinal = 8)
	private Date createTime;


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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}

	public Comment() {
	}

	public Comment(Long id, Long userid, Long parentId, Long targetId, String content, Date createTime, boolean state, int liked) {
		this.id = id;
		this.userid = userid;
		this.parentId = parentId;
		this.targetId = targetId;
		this.content = content;
		this.createTime = createTime;
		this.state = state;
		this.liked = liked;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", userId=" + userid +
				", partenId=" + parentId +
				", targetId=" + targetId +
				", state=" + state +
				", liked=" + liked +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				'}';
	}


}
