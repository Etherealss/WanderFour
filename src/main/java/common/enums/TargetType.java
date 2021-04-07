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
	/** 评论 */
	COMMMENT(3, "comment"),
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
		return -1;
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
		return null;
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

	/**
	 * 根据LikeRecord的targetType获取对应的点赞数据库表名
	 * @param targetType
	 * @return
	 */
	public static String getLikeTableNameByTargetType(TargetType targetType) {
		String likeTableName;
		// 文章点赞表
		String likeArticleTableName = "`article_like_record`";
		// 问贴点赞表
		String likePostsTableName = "`posts_like_record`";
		// 评论点赞表
		String likeCommentTableName = "`posts_like_record`";
		switch (targetType) {
			case ARTICLE:
				likeTableName = likeArticleTableName;
				break;
			case POSTS:
				likeTableName = likePostsTableName;
				break;
			default:
				likeTableName = likeCommentTableName;
				break;
		}
		return likeTableName;
	}
}
