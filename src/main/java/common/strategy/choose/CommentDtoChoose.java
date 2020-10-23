package common.strategy.choose;

import common.strategy.GetCommentsStrategy;
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
	private GetCommentsStrategy strategy;

	/**
	 * @param strategy 获取评论的策略
	 */
	public CommentDtoChoose(GetCommentsStrategy strategy){
		this.strategy = strategy;
	}

	/**
	 * 获取评论Dto
	 * @param vo
	 */
	public List<CommentDto> getCommentDtoList(CommentVo vo) throws SQLException {
		return strategy.getComments(vo);
	}
}
