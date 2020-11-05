package common.enums;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/24
 */
public class DaoEnum {

	/**
	 * 数据库的Limit从0开始获取记录
	 */
	public static final Long START_FROM_ZERO = 0L;
	/**
	 * 每页获取6条文章和问贴记录
	 */
	public static final int ROWS_SIX = 6;

	/**
	 * 每页获取5条文章和问贴记录
	 */
	public static final int ROWS_FIVE = 5;
	/**
	 * 每条评论获取3条回复
	 */
	public static final int REPLY_ROWS_THREE = 3;
	/**
	 * 每页显示3条评论
	 */
	public static final int COMMENT_ROWS_THREE = 3;
	/**
	 * 每条评论获取10条回复
	 */
	public static final int REPLY_ROWS_TEN = 10;

	public static final int ROWS_ZERO = 0;
	/**
	 * 每页显示10条评论
	 */
	public static final int COMMENT_ROWS_TEN = 10;
	/**
	 * 数据库表字段名
	 */
	public static final String FIELD_ORDER_BY_LIKE = "`liked`";
	/**
	 * 数据库表字段名
	 */
	public static final String FIELD_ORDER_BY_TIME = "`create_time`";
	/**
	 * 排序方式
	 */
	public static final String ORDER_BY_LIKE = "like";
	/**
	 * 排序方式
	 */
	public static final String ORDER_BY_TIME = "time";
}
