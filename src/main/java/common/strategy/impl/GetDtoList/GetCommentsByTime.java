package common.strategy.impl.GetDtoList;

import common.strategy.GetCommentsStrategy;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 策略：在显示所有评论时获取评论
 * 获取10条最新的评论，并同时每条评论获取3条点赞数最高的回复
 * @date 2020/10/23
 */
public class GetCommentsByTime extends GetCommentsStrategy {
	/**
	 * 数据库表字段名
	 */
	private static final String ORDER_BY_TIME = "`create_time`";


	@Override
	public List<CommentDto> getComments(CommentVo vo) throws SQLException {
		vo.setOrder(ORDER_BY_TIME);
		vo.setCommentRows(COMMENT_ROWS_TEN);
		vo.setReplyRows(REPLY_ROWS_TEN);
		vo.setStart(START_FROM_ZERO);
		List<CommentDto> returnList = getCommentDto(vo);
		return returnList;
	}
}
