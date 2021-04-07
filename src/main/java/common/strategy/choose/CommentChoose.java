package common.strategy.choose;

import common.strategy.AbstractCommentsStrategy;
import pojo.vo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取评论的策略选择
 * @date 2020/10/23
 */
public class CommentChoose {

	/**
	 * 获取评论的策略
	 */
	private AbstractCommentsStrategy strategy;

	/**
	 * @param strategy 获取评论的策略
	 */
	public CommentChoose(AbstractCommentsStrategy strategy){
		this.strategy = strategy;
	}

	/**
	 * 获取评论Dto
	 * @param vo
	 * 需要conn dao userid parentId commentRows replyRows start order
	 */
	public List<CommentDto> doGet(CommentVo vo) {
		return strategy.getCommentsWithReplys(vo);
	}
}
