package pojo.po;

import common.annontation.Db;
import common.annontation.DbField;
import common.annontation.DbFieldId;
import common.annontation.DbTable;

import java.util.Date;

/**
 * @author 寒洲
 * @description 文章
 * @date 2020/10/5
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
public class Article {
	@DbFieldId
	@DbField("a_id")
	private Long id;
	@DbField("a_partition")
	private String partition;// 社区分区
	@DbField("a_category")
	private String category;// 二级分类
	@DbField("a_authod_id")
	private String authorId;
	@DbField("a_title")
	private String title;
	@DbField("a_content")
	private String content;
	@DbField("a_label1")
	private String label1;
	@DbField("a_label2")
	private String label2;
	@DbField("a_label3")
	private String label3;
	@DbField("a_label4")
	private String label4;
	@DbField("a_label5")
	private String label5;
	@DbField("a_create_time")
	private Date createTime;
	@DbField("a_update_time")
	private Date updateTime;
	@DbField("a_liked")
	private int liked;//点赞数
	@DbField("a_collected")
	private int collected;//收藏数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}

	public int getCollected() {
		return collected;
	}

	public void setCollected(int collected) {
		this.collected = collected;
	}

	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", partition='" + partition + '\'' +
				", category='" + category + '\'' +
				", authorId='" + authorId + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", label1='" + label1 + '\'' +
				", label2='" + label2 + '\'' +
				", label3='" + label3 + '\'' +
				", label4='" + label4 + '\'' +
				", label5='" + label5 + '\'' +
				", creatTime=" + createTime +
				", updateTime=" + updateTime +
				", liked=" + liked +
				", collected=" + collected +
				'}';
	}

	/**
	 * @param id
	 * @param partition
	 * @param category
	 * @param authorId
	 * @param title
	 * @param content
	 * @param label1
	 * @param label2
	 * @param label3
	 * @param label4
	 * @param label5
	 * @param createTime
	 * @param updateTime
	 * @param liked
	 * @param collected
	 */
	public Article(Long id, String partition, String category, String authorId, String title,
	               String content, String label1, String label2, String label3, String label4, String label5,
	               Date createTime, Date updateTime, int liked, int collected) {
		this.id = id;
		this.partition = partition;
		this.category = category;
		this.authorId = authorId;
		this.title = title;
		this.content = content;
		this.label1 = label1;
		this.label2 = label2;
		this.label3 = label3;
		this.label4 = label4;
		this.label5 = label5;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.liked = liked;
		this.collected = collected;

	}
}
