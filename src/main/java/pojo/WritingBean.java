package pojo;

import common.enums.WritingEnum;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
public class WritingBean {
	Writing writing; //对应数据库的实体
	WritingEnum writingType; //作品类型

	public Writing getWriting() {
		return writing;
	}

	public void setWriting(Writing writing) {
		this.writing = writing;
	}

	public WritingEnum getWritingType() {
		return writingType;
	}

	public void setWritingType(WritingEnum writingType) {
		this.writingType = writingType;
	}

	public WritingBean(Writing writing, WritingEnum writingType) {
		this.writing = writing;
		this.writingType = writingType;
	}

	public WritingBean() {
	}
}
