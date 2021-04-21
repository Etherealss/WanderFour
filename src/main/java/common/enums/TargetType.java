package common.enums;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public enum TargetType {
	/** 文章 */
	ARTICLE(1, "article"),
	/** 帖子 */
	POSTS(2, "posts"),
	/** 文章评论 */
	ARTICLE_COMMMENT(3, "articleComment"),
	/** 问贴 */
	POSTS_COMMENT(4, "postsComment")
	;

	private final int CODE;
	private final String VALUE;

	TargetType(int code, String value) {
		CODE = code;
		VALUE = value;
	}

	public int code() {
		return CODE;
	}

	public String val() {
		return VALUE;
	}

	/**
	 * 通过字符串获取数值
	 * @param value
	 * @return code
	 */
	public static int getCode(String value) {
		for (TargetType p : TargetType.values()) {
			if (p.val().equals(value)) {
				return p.code();
			}
		}
		throw new IllegalArgumentException("无效的枚举类型参数");
	}

	/**
	 * 通过字符串获取枚举
	 * @param value
	 * @return
	 */
	public static TargetType getTargetType(String value) {
		for (TargetType p : TargetType.values()) {
			if (p.val().equals(value)) {
				return p;
			}
		}
		throw new IllegalArgumentException("无效的枚举类型参数");
	}

	/**
	 * 通过数字获取枚举
	 * @param value
	 * @return
	 */
	public static TargetType getTargetType(int value) {
		for (TargetType p : TargetType.values()) {
			if (p.code() == value) {
				return p;
			}
		}
		return null;
	}
	/** 文章点赞表 */
	private static final String TABLE_NAME_LIKE_ARTICLE = "`article_like_record`";
	/** 问贴点赞表 */
	private static final String TABLE_NAME_LIKE_POSTS = "`posts_like_record`";
	/** 文章评论点赞表 */
	private static final String TABLE_NAME_LIKE_ARTICLE_COMMENT = "`article_comment_like_record`";
	/** 问贴评论点赞表 */
	private static final String TABLE_NAME_LIKE_POSTS_COMMENT = "`posts_comment_like_record`";

	/**
	 * 根据LikeRecord的targetType获取对应的点赞数据库表名
	 * @param targetType
	 * @return
	 */
	public static String getLikeTableNameByTargetType(TargetType targetType) {
		String likeTableName;
		switch (targetType) {
			case ARTICLE:
				likeTableName = TABLE_NAME_LIKE_ARTICLE;
				break;
			case POSTS:
				likeTableName = TABLE_NAME_LIKE_POSTS;
				break;
			case ARTICLE_COMMMENT:
				likeTableName = TABLE_NAME_LIKE_ARTICLE_COMMENT;
				break;
			case POSTS_COMMENT:
			default:
				likeTableName = TABLE_NAME_LIKE_POSTS_COMMENT;
				break;
		}
		return likeTableName;
	}
}
