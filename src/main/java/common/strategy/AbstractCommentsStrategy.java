package common.strategy;

import common.enums.DaoEnum;
import common.util.CommentUtil;
import dao.CommentDao;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.CommentBean;
import pojo.dto.CommentDto;
import pojo.po.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 获取评论的抽象策略
 * 主要作用是提供给策略实现类一个方法getCommentDto
 * 策略实现类将确定参数并调用getCommentDto方法
 * @date 2020/10/23
 */
public abstract class AbstractCommentsStrategy extends AbstractCommentAndReplyStrategy {

	protected Logger logger = Logger.getLogger(AbstractCommentsStrategy.class);

	/**
	 * 装饰方法
	 * 获取经过包装的评论列表，评论带回复
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	public abstract List<CommentDto> getCommentsWithReplys(CommentVo vo) throws SQLException;

	/**
	 * 获取CommentDto列表
	 * @param vo 封装函数参数的对象
	 *           需要传入 dao conn order parentId userId
	 * @return
	 * @throws SQLException
	 */
	public List<CommentDto> getCommentDto(CommentVo vo) throws SQLException {
		CommentDao dao = vo.getDao();
		Connection conn = vo.getConn();
		String order = vo.getOrder();
		Long parentId = vo.getParentId();
		Long userid = vo.getUserid();
		//结果列表
		List<CommentDto> returnDtoList = new ArrayList<>();

		//获取rows条 顶层评论 数据
		List<Comment> commentList = dao.getCommentList(
				conn, order, vo.getCommentStart(), vo.getCommentRows(), parentId);

		/*
		加工评论数据：
		1、封装CommentBean，包括评论信息、评论用户昵称和头像
		2、封装回复的评论信息
			2.1封装回复的评论的CommentBean
		3、将commentBean, replysCommentBean封装到Dto，添加到要返回的list中
		 */
		for (Comment comment : commentList) {
			Long targetId = comment.getTargetId();
			/*
			包装 顶层评论的Bean
			1、parentId和targetId不相同，说明该评论是回复其他评论
			则查询回复的评论，添加引用(reply)
			2、parentId和targetId相同，说明该回复是回复评论，不引用
			获取的targetComment为null，不影响封装
			 */
			Comment targetComment = dao.getComment(conn, targetId);
			CommentBean commentBean = CommentUtil.getCommentBean(conn, comment, targetComment, userid);

			/*
			获取该评论的回复记录信息列表
			TODO 此处按获赞数获取回复
			 */
			CommentVo voForReply = vo;
			voForReply.setOrder(DaoEnum.FIELD_ORDER_BY_LIKE);
			List<CommentBean> replysCommentBean = getReplysCommentBean(voForReply, false);
			//封装到Dto
			CommentDto dto = new CommentDto(commentBean, replysCommentBean);
			returnDtoList.add(dto);
		}
		return returnDtoList;
	}
}
