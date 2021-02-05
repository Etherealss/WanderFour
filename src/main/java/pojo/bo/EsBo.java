package pojo.bo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/16
 */
public class EsBo {

	@JSONField(ordinal = 0)
	private Long writingId;
	@JSONField(ordinal = 1)
	private String writingType;

	@JSONField(ordinal = 3)
	private Long authorId;

	@JSONField(ordinal = 4)
	private Integer categoryId;
	@JSONField(ordinal = 5)
	private String categoryName;

	@JSONField(ordinal = 6)
	private String title;

	@JSONField(ordinal = 11)
	private String content;

	@JSONField(ordinal = 7)
	private String label1;

	@JSONField(ordinal = 7)
	private String label2;

	@JSONField(ordinal = 7)
	private String label3;

	@JSONField(ordinal = 7)
	private String label4;

	@JSONField(ordinal = 7)
	private String label5;

	@JSONField(ordinal = 8)
	private Date createTime;

	@JSONField(ordinal = 9)
	private Date updateTime;

	@JSONField(ordinal = 10)
	private Integer liked;

	@JSONField(ordinal = 10)
	private Integer collected;


	public EsBo() {
	}

	@Override
	public String toString() {
		return "EsBo{" +
				"writingId=" + writingId +
				", writingType='" + writingType + '\'' +
				", categoryId=" + categoryId +
				", categoryName='" + categoryName + '\'' +
				", arthorId=" + authorId +
				", title='" + title + '\'' +
				", label1='" + label1 + '\'' +
				", label2='" + label2 + '\'' +
				", label3='" + label3 + '\'' +
				", label4='" + label4 + '\'' +
				", label5='" + label5 + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", liked=" + liked +
				", collected=" + collected +
				", content='" + content + '\'' +
				'}';
	}

	public Long getWritingId() {
		return writingId;
	}

	public void setWritingId(Long writingId) {
		this.writingId = writingId;
	}

	public String getWritingType() {
		return writingType;
	}

	public void setWritingType(String writingType) {
		this.writingType = writingType;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getLabel3() {
		return label3;
	}

	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	public String getLabel4() {
		return label4;
	}

	public void setLabel4(String label4) {
		this.label4 = label4;
	}

	public String getLabel5() {
		return label5;
	}

	public void setLabel5(String label5) {
		this.label5 = label5;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getLiked() {
		return liked;
	}

	public void setLiked(Integer liked) {
		this.liked = liked;
	}

	public Integer getCollected() {
		return collected;
	}

	public void setCollected(Integer collected) {
		this.collected = collected;
	}

	public String[] getAllLabels() {
		return new String[]{label1, label2, label3, label4, label5};
	}
}
