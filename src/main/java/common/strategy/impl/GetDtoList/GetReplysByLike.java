package common.strategy.impl.GetDtoList;

import common.enums.CommentEnum;
import pojo.CommentVo;
import pojo.dto.ReplyDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/24
 */
public class GetReplysByLike extends GetReplysStrategy {

	@Override
	public List<ReplyDto> getReplys(CommentVo vo) throws SQLException {
		/*
		策略：在显示评论的所有回复时获取回复
	    获取10条最新回复
        不添加回复引用
		 */
		vo.setOrder(CommentEnum.FIELD_ORDER_BY_LIKE);

		//10+3
		vo.setReplyRows(CommentEnum.REPLY_ROWS_TEN);

		//从0开始
		vo.setReplyStart(CommentEnum.START_FROM_ZERO);

		//不添加回复引用
		List<ReplyDto> returnList = getReplys(vo, false);
		return returnList;
	}
}
