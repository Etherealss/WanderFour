package common.enums;

/**
 * @author 寒洲
 * @description 作品类型枚举
 * @date 2020/10/8
 */
public enum WritingType {
	/** 学习天地的文章 */
	LEARNING_ARTICLE(1, "学习天地的文章"),
	/** 专业介绍的文章 */
	COLLEGE_ARTICLE(2, "专业介绍的文章"),
	/** 大学风采的文章 */
	MAJOR_ARTICLE(3,"大学风采的文章"),
	/** 学习天地的帖子 */
	LEARNING_POSTS(4, "学习天地的帖子"),
	/** 专业介绍的帖子 */
	COLLEGE_POSTS(5, "专业介绍的帖子"),
	/** 大学风采的帖子 */
	MAJOR_POSTS(6,"大学风采的帖子"),
	;

	private final int CODE;
	private final String VALUE;

	WritingType(int code, String value){
		CODE = code;
		VALUE = value;
	}

	public int code() {
		return CODE;
	}

	public String val() {
		return VALUE;
	}
}

