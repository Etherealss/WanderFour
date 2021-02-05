package pojo.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author 寒洲
 * @description 便利贴
 * @date 2020/11/20
 */
public class StickyNote {
	@JSONField(ordinal = 0)
	private Long id;

	@JSONField(ordinal = 1)
	private Long authorId;

	@JSONField(ordinal = 2)
	private String content;

	/** 形状 */
	@JSONField(ordinal = 3)
	private String shape;

	/** ARBG 透明度 */
	@JSONField(ordinal = 4)
	private short alpha;

	/** ARBG red */
	@JSONField(ordinal = 5)
	private short red;

	/** ARBG green */
	@JSONField(ordinal = 6)
	private short green;

	/** ARBG blue */
	@JSONField(ordinal = 7)
	private short blue;

	/** 顺时针偏转角度 */
	@JSONField(ordinal = 8)
	private short angle;

	@JSONField(ordinal = 9)
	private Date createTime;

	@JSONField(ordinal = 10)
	private Integer likeCount;


	@Override
	public String toString() {
		return "StickyNote{" +
				"id=" + id +
				", authorId=" + authorId +
				", content='" + content + '\'' +
				", shape='" + shape + '\'' +
				", alpha=" + alpha +
				", red=" + red +
				", green=" + green +
				", blue=" + blue +
				", angle=" + angle +
				", createTime=" + createTime +
				", likeCount=" + likeCount +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
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

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public short getAlpha() {
		return alpha;
	}

	public void setAlpha(short alpha) {
		this.alpha = alpha;
	}

	public short getRed() {
		return red;
	}

	public void setRed(short red) {
		this.red = red;
	}

	public short getGreen() {
		return green;
	}

	public void setGreen(short green) {
		this.green = green;
	}

	public short getBlue() {
		return blue;
	}

	public void setBlue(short blue) {
		this.blue = blue;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public short getAngle() {
		return angle;
	}

	public void setAngle(short angle) {
		this.angle = angle;
	}

	public StickyNote() {
	}
}
