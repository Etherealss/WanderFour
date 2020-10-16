package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.annontation.DbTableFK;
import common.enums.Partition;

import java.util.Date;
import java.util.Objects;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
@DbTableFK(foreignKey = {"user", "partition", "article_content"})
public class Article extends Writing {
	/** 数据库表id */
	protected Long id;

//	protected String partitionStr;
	/** 分类 */
	protected String category;
	/** 用户id */
	protected String authorId;
	protected String title;
	protected String label1;
	protected String label2;
	protected String label3;
	protected String label4;
	protected String label5;
	protected Date updateTime;
	protected Date createTime;
	/** 点赞数 */
	protected int liked;
	/** 收藏数 */
	protected int collected;

	protected String content;
	/** 社区分区 */
	protected Partition partition;

	public Article() {
	}

	public Article(Long id, String category, String authorId,
	               String title, String label1, String label2, String label3, String label4,
	               String label5, Date updateTime, Date createTime, int liked, int collected,
	               String content, Partition partition) {
		this.id = id;
		this.category = category;
		this.authorId = authorId;
		this.title = title;
		this.label1 = label1;
		this.label2 = label2;
		this.label3 = label3;
		this.label4 = label4;
		this.label5 = label5;
		this.updateTime = updateTime;
		this.createTime = createTime;
		this.liked = liked;
		this.collected = collected;
		this.content = content;
		this.partition = partition;
	}

	public Article(String category, String authorId, String title,
	               String label1, String label2, String label3, String label4, String label5,
	               String content) {
		this.category = category;
		this.authorId = authorId;
		this.title = title;
		this.label1 = label1;
		this.label2 = label2;
		this.label3 = label3;
		this.label4 = label4;
		this.label5 = label5;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", category='" + category + '\'' +
				", authorId='" + authorId + '\'' +
				", title='" + title + '\'' +
				", label1='" + label1 + '\'' +
				", label2='" + label2 + '\'' +
				", label3='" + label3 + '\'' +
				", label4='" + label4 + '\'' +
				", label5='" + label5 + '\'' +
				", updateTime=" + updateTime +
				", createTime=" + createTime +
				", liked=" + liked +
				", collected=" + collected +
				", content='" + content + '\'' +
				", partition=" + partition +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


//	public void setPartitionStr(String partitionStr) {
//		this.partitionStr = partitionStr;
//		//不调用set函数避免死循环
//		if ("".equals(partitionStr) || partitionStr!=null){
//			partition = Partition.getPartition(partitionStr);
//			System.out.println();
//			System.out.println(partition);
//		}
//	}

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Partition getPartition() {
		return partition;

	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public void setPartition(String partitionStr) {
		this.partition = Partition.getPartition(partitionStr);
		System.out.println("setPartition = " + partition);
	}
}
