package common.strategy.impl.GetDtoList;

import common.strategy.GetCommentsStrategy;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 策略：在文章加载时获取评论
 * 获取3条点赞数最高的评论，并同时每条评论获取3条点赞数最高的回复
 * @date 2020/10/23
 */
public class GetHeadCommentsByLike extends GetCommentsStrategy {

	/**
	 * 数据库表字段名
	 */
	private static final String ORDER_BY_LIKE = "`liked`";

	/**
	 * @param vo 需要 dao conn parentId userId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<CommentDto> getComments(CommentVo vo) throws SQLException {
		vo.setOrder(ORDER_BY_LIKE);
		vo.setCommentRows(COMMENT_ROWS_THREE);
		vo.setReplyRows(REPLY_ROWS_THREE);
		vo.setStart(START_FROM_ZERO);
		List<CommentDto> returnList = getCommentDto(vo);
		return returnList;
	}
}
