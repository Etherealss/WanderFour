package common.strategy.choose;

import common.strategy.AbstractReplysStrategy;
import pojo.vo.CommentVo;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取回复的策略选择
 * 因为使用AbstractCommentAndReplyStrategy时，无法用一个Choose完成评论和回复的策略操作
 * 所以被迫分成两个Choose
 * @date 2020/10/24
 */
public class ReplyChoose {
	/**
	 * 获取评论的策略
	 */
	private AbstractReplysStrategy strategy;

	/**
	 * @param strategy 获取评论的策略
	 */
	public ReplyChoose(AbstractReplysStrategy strategy){
		this.strategy = strategy;
	}

	/**
	 * 获取评论Dto
	 * @param vo
	 * 需要conn dao userid parentId commentRows replyRows start order
	 */
	public List<CommentDto> doGet(CommentVo vo) {
		return strategy.getReplys(vo);
	}
}
