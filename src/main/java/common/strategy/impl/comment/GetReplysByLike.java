package common.strategy.impl.comment;

import common.enums.DaoEnum;
import common.strategy.AbstractReplysStrategy;
import pojo.vo.CommentVo;
import pojo.dto.CommentDto;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/24
 */
public class GetReplysByLike extends AbstractReplysStrategy {

	@Override
	public List<CommentDto> getReplys(CommentVo vo) {
		/*
		策略：在显示评论的所有回复时获取回复
	    获取10条最新回复
        不添加回复引用
		 */
		vo.setOrder(DaoEnum.FIELD_ORDER_BY_LIKE);

		//10+3
		vo.setReplyRows(DaoEnum.REPLY_ROWS_TEN);

		List<CommentDto> returnList = getReplysDtoList(vo);
		return returnList;
	}
}
