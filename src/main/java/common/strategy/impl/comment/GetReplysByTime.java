package common.strategy.impl.comment;

import common.enums.CommentEnum;
import common.strategy.AbstractReplysStrategy;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/24
 */
public class GetReplysByTime extends AbstractReplysStrategy {

	@Override
	public List<CommentDto> getReplys(CommentVo vo) throws SQLException {
		/*
		策略：在显示评论的所有回复时获取回复
	    获取10条最新回复
        不添加回复引用
		 */
		vo.setOrder(CommentEnum.FIELD_ORDER_BY_TIME);

		//10+3
		vo.setReplyRows(CommentEnum.REPLY_ROWS_TEN);

		//不添加回复引用
		List<CommentDto> returnList = getReplysDtoList(vo);
		return returnList;
	}
}
