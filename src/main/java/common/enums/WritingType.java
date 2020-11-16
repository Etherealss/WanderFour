package common.enums;

import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description 区分 文章 / 问贴 的枚举
 * 这里的枚举和 TargetType 很像，但是如果使用TargetType，起不到限制输入的作用（可以输入COOMMENT）
 * @date 2020/11/15
 */
public enum WritingType {
	/** 文章 */
	ARTICLE("article", Article.class),
	/** 问题 */
	POSTS("posts", Posts.class),
	;

	private final String VALUE;
	private final Class<? extends Writing> CLAZZ;

	WritingType(String value, Class<? extends Writing> clazz) {
		this.VALUE = value;
		this.CLAZZ = clazz;
	}

	public String val() {
		return VALUE;
	}

	public Class<? extends Writing> getClazz() {
		return CLAZZ;
	}
}
