package common.strategy.impl.comment;

import common.enums.DaoEnum;
import common.strategy.AbstractCommentsStrategy;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 策略：仅按点赞数获取3条评论记录（不获取其回复）
 * @date 2020/11/4
 */
public class GetOnlyCommentByLike extends AbstractCommentsStrategy {
	@Override
	public List<CommentDto> getCommentsWithReplys(CommentVo vo) throws SQLException {
		/*
		 * 仅按点赞数获取3条评论记录（不获取其回复）
		 */
		vo.setOrder(DaoEnum.FIELD_ORDER_BY_LIKE);

		//3+0
		vo.setCommentRows(DaoEnum.COMMENT_ROWS_THREE);
		//不获取回复
		vo.setReplyRows(DaoEnum.ROWS_ZERO);

		//都从0开始
		vo.setCommentStart(DaoEnum.START_FROM_ZERO);
		vo.setReplyStart(DaoEnum.START_FROM_ZERO);

		List<CommentDto> returnList = getCommentDto(vo);
		return returnList;
	}
}
