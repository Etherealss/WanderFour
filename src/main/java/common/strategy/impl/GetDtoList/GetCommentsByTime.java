package common.strategy.impl.GetDtoList;

import common.enums.CommentEnum;
import common.strategy.GetCommentsStrategyDecorator;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 策略：在显示所有评论时获取评论
 * 获取10条最新的评论，并同时每条评论获取3条点赞数最高的回复
 * 不添加回复引用
 * @date 2020/10/23
 */
public class GetCommentsByTime extends GetCommentsStrategyDecorator {



	@Override
	public List<CommentDto> getComments(CommentVo vo) throws SQLException {
		/*
		策略：在显示所有评论时获取评论
	    获取10条最新的评论，并同时每条评论获取3条点赞数最高的回复
        不添加回复引用
		 */
		vo.setOrder(CommentEnum.FIELD_ORDER_BY_TIME);

		// 10+3
		vo.setCommentRows(CommentEnum.COMMENT_ROWS_TEN);
		vo.setReplyRows(CommentEnum.REPLY_ROWS_THREE);

		//从0开始
		vo.setCommentStart(CommentEnum.START_FROM_ZERO);
		vo.setReplyStart(CommentEnum.START_FROM_ZERO);

		//不添加回复引用
		List<CommentDto> returnList = getCommentDto(vo, false);
		return returnList;
	}
}
