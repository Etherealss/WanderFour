package common.strategy;

import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.CommentBean;
import pojo.dto.CommentDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取回复的策略
 * @date 2020/10/24
 */
public abstract class AbstractReplysStrategy extends AbstractCommentAndReplyStrategy {

	protected Logger logger = Logger.getLogger(AbstractCommentsStrategy.class);

	/**
	 * 获取请求的数据（回复列表）
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	public abstract List<CommentDto> getReplys(CommentVo vo) throws SQLException;

	/**
	 * 获取回复的Dto列表
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	public List<CommentDto> getReplysDtoList(CommentVo vo) throws SQLException {

		//获取回复的数据列表
		List<CommentBean> replysCommentBean = getReplysCommentBean(vo, true);
		//封装后添加到List<ReplyDto>中返回
		List<CommentDto> list = new ArrayList<>();
		for (CommentBean commentBean : replysCommentBean) {
			CommentDto dto = new CommentDto();
			//只包装该回复记录，不添加其他replys
			dto.setParentComment(commentBean);

			list.add(dto);
		}
		return list;
	}

}
