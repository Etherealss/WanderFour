package pojo;

import common.enums.Partition;
import common.enums.WritingType;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class WritingBean {
	Writing writing; //对应数据库的实体
	WritingType writingType; //作品类型

	public Writing getWriting() {
		return writing;
	}

	public void setWriting(Writing writing) {
		this.writing = writing;
	}

	public WritingType getWritingType() {
		return writingType;
	}

	public void setWritingType(WritingType writingType) {
		this.writingType = writingType;
	}

	public WritingBean(Writing writing, WritingType writingType) {
		this.writing = writing;
		this.writingType = writingType;
	}

	public WritingBean() {
	}
}
