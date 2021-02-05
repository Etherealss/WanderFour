package pojo.po;

import common.enums.Partition;

import java.util.Date;

/**
 * @author 寒洲
 * @description 作品类，文章和帖子的基类
 * @date 2020/10/7
 */
public class Writing {

	/** 数据库表id */
	protected Long id;
	/** 分类 */
	protected int category;
	/** 用户id */
	protected Long authorId;
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
	protected String partitionStr;

	public Writing() {
	}

	@Override
	public String toString() {
		String str = "Writing{id=" + id + ", partition=";
		if (partition != null) {
			str += partition.val();
		} else {
			str += "null";
		}
		str += ", category='" + category + '\'' +
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
				", content='" + content + "'}";
		return str;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
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

	/**
	 * @param partitionStr
	 */
	public void setPartitionStr(String partitionStr){
		/*
		QueryRunner无法直接调用setPartition(Partition)给枚举赋值
		使用这个方法让QueryRunner调用该方法给partition枚举赋值
		 */
		setPartition(partitionStr);
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public void setPartition(String partitionStr) {
		this.partition = Partition.getPartition(partitionStr);
	}


	public <T extends Writing>T toOthers(T t){
		t.setId(id);
		t.setCategory(category);
		t.setAuthorId(authorId);
		t.setTitle(title);
		t.setLabel1(label2);
		t.setLabel2(label2);
		t.setLabel3(label3);
		t.setLabel4(label4);
		t.setLabel5(label5);
		t.setUpdateTime(updateTime);
		t.setCreateTime(createTime);
		t.setLiked(liked);
		t.setCollected(collected);
		t.setContent(content);
		t.setPartition(partition);
		t.setPartitionStr(partitionStr);
		return t;
	}
}
