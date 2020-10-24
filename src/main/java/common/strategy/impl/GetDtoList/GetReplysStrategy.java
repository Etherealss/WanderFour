package common.strategy.impl.GetDtoList;

import common.strategy.GetCommentsStrategyDecorator;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.CommentBean;
import pojo.dto.ReplyDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取回复的策略
 * @date 2020/10/24
 */
public abstract class GetReplysStrategy extends CommentAndReplyStrategy {
	protected Logger logger = Logger.getLogger(GetCommentsStrategyDecorator.class);

	/**
	 * 获取CommentDto列表
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ReplyDto> getReplys(CommentVo vo) throws SQLException;

	/**
	 * 获取回复的Dto列表
	 * @param vo
	 * @param addReplyReference
	 * @return
	 * @throws SQLException
	 */
	public List<ReplyDto> getReplys(CommentVo vo, boolean addReplyReference) throws SQLException {

		//获取回复的数据列表
		List<CommentBean> replysCommentBean = getReplysCommentBean(vo, addReplyReference);
		//封装后添加到List<ReplyDto>中返回
		List<ReplyDto> list = new ArrayList<>();
		for (CommentBean commentBean : replysCommentBean) {
			ReplyDto dto = new ReplyDto(commentBean);
			list.add(dto);
		}
		return list;
	}

}
