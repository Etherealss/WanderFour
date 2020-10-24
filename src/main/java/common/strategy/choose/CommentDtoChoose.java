package common.strategy.choose;

import common.strategy.GetCommentsStrategyDecorator;
import pojo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/23
 */
public class CommentDtoChoose {


	/**
	 * 获取评论的策略
	 */
	private GetCommentsStrategyDecorator strategy;

	/**
	 * @param strategy 获取评论的策略
	 */
	public CommentDtoChoose(GetCommentsStrategyDecorator strategy){
		this.strategy = strategy;
	}

	/**
	 * 获取评论Dto
	 * @param vo
	 * 需要conn dao userid parentId commentRows replyRows start order
	 */
	public List<CommentDto> getCommentDtoList(CommentVo vo) throws SQLException {
		return strategy.getComments(vo);
	}
}
