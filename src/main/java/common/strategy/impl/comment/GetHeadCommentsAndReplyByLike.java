package common.strategy.impl.comment;

import common.enums.DaoEnum;
import common.strategy.AbstractCommentsStrategy;
import pojo.vo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 策略：在文章加载时获取评论
 * 获取3条点赞数最高的评论，并同时每条评论获取3条点赞数最高的回复
 * 不添加回复引用
 * @date 2020/10/23
 */
public class GetHeadCommentsAndReplyByLike extends AbstractCommentsStrategy {

	/**
	 * @param vo 需要 dao conn parentId userId
	 * @return
	 */
	@Override
	public List<CommentDto> getCommentsWithReplys(CommentVo vo) {
		/*
		 * 策略：在文章加载时获取评论
         * 获取3条点赞数最高的评论，并同时每条评论获取3条点赞数最高的回复
         * 不添加回复引用
		 */
		vo.setOrder(DaoEnum.FIELD_ORDER_BY_LIKE);

		//3+3
		vo.setCommentRows(DaoEnum.COMMENT_ROWS_THREE);
		vo.setReplyRows(DaoEnum.REPLY_ROWS_THREE);

		//都从0开始
		vo.setCommentStart(DaoEnum.START_FROM_ZERO);
		vo.setReplyStart(DaoEnum.START_FROM_ZERO);

		List<CommentDto> returnList = getCommentDto(vo);
		return returnList;
	}

}
