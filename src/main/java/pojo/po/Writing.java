package pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.annontation.DbField;
import common.annontation.DbFieldId;
import common.enums.Partition;

import java.util.Date;
import java.util.Objects;

/**
 * @author 寒洲
 * @description 作品类，文章和帖子的基类
 * @date 2020/10/7
 */
public class Writing {
	@DbFieldId
	@DbField("id")
	protected Long id;
	@DbField("partition")
	protected String partitionStr;// 社区分区
	@DbField("category")
	protected String category;// 二级分类
	@DbField("authod_id")
	protected String authorId;
	@DbField("title")
	protected String title;
	@DbField("label1")
	protected String label1;
	@DbField("label2")
	protected String label2;
	@DbField("label3")
	protected String label3;
	@DbField("label4")
	protected String label4;
	@DbField("label5")
	protected String label5;
	@DbField("update_time")
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;
	@DbField("create_time")
	protected Date createTime;
	@DbField("liked")
	protected int liked;//点赞数
	@DbField("collected")
	protected int collected;//收藏数

	protected String content;
	protected Partition partition;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getPartitionStr() {
		return partitionStr;
	}

	public void setPartitionStr(String partitionStr) {
		this.partitionStr = partitionStr;
		//不调用函数避免死循环
		System.out.println(partitionStr);
		partition = (Objects.requireNonNull(Partition.getPartition(partitionStr)));
	}

	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
		//不调用函数避免死循环
		partitionStr = partition.val();
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
		return "Writing{" +
				"id=" + id +
				", partitionStr='" + partitionStr + '\'' +
				", category='" + category + '\'' +
				", authorId='" + authorId + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", label1='" + label1 + '\'' +
				", label2='" + label2 + '\'' +
				", label3='" + label3 + '\'' +
				", label4='" + label4 + '\'' +
				", label5='" + label5 + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", liked=" + liked +
				", collected=" + collected +
				", partition=" + partition +
				'}';
	}

	public Writing() {}

	public Writing(String category, String authorId, String title, String content,
	               String label1, String label2, String label3, String label4, String label5,
	               Date createTime, Date updateTime, Partition partition) {
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
		this.partition = partition;
	}
}
