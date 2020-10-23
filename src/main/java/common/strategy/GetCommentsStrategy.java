package common.strategy;

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
 * @description
 * @date 2020/10/23
 */
public abstract class GetCommentsStrategy {

	protected Logger logger = Logger.getLogger(GetCommentsStrategy.class);
	/**
	 * 数据库的Limit从0开始获取记录
	 */
	protected static final Long START_FROM_ZERO = 0L;

	/**
	 * 每条评论获取3条回复
	 */
	protected static final int REPLY_ROWS_THREE = 3;
	/**
	 * 每页显示3条评论
	 */
	protected static final int COMMENT_ROWS_THREE = 3;
	/**
	 * 每条评论获取10条回复
	 */
	protected static final int REPLY_ROWS_TEN = 10;
	/**
	 * 每页显示10条评论
	 */
	protected static final int COMMENT_ROWS_TEN = 10;

	private CommentDao dao;
	private Connection conn;

	/**
	 * 获取CommentDto列表
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	public abstract List<CommentDto> getComments(CommentVo vo) throws SQLException;

	/**
	 * 获取CommentDto列表
	 * @param vo 封装函数参数的对象
	 *           需要 dao conn order parentId userId
	 * @return
	 * @throws SQLException
	 */
	public List<CommentDto> getCommentDto(CommentVo vo) throws SQLException {
		dao = vo.getDao();
		conn = vo.getConn();
		String order = vo.getOrder();
		Long parentId = vo.getParentId();
		Long userid = vo.getUserid();
		//结果列表
		List<CommentDto> returnDtoList = new ArrayList<>();

		//获取rows条 顶层评论 数据
		List<Comment> commentList = dao.getCommentList(conn, order, vo.getStart(), vo.getCommentRows(), parentId);

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
			1、parentId和targetId不相同，说明该评论是回复其他评论
			则查询回复的评论，添加引用(reply)
			2、parentId和targetId相同，说明该回复是回复评论，不引用
			获取的targetComment为null，不影响封装
			 */
			Comment targetComment = dao.getComment(conn, targetId);
			CommentBean commentBean = CommentUtil.getCommentBean(conn, comment, targetComment, userid);
			//获取该评论的回复记录信息
			List<CommentBean> replysCommentBean = getReplysCommentBean(order, parentId, userid);
			//封装到Dto
			CommentDto dto = new CommentDto(commentBean, replysCommentBean);
			returnDtoList.add(dto);
		}
		return returnDtoList;
	}

	/**
	 * 获取某一评论下的多条回复记录
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	private List<CommentBean> getReplysCommentBean(String orderBy, Long parentId, Long userid) throws SQLException {
		List<CommentBean> list = new ArrayList<>();

		//按需要的记录数 获取 点赞数高的 回复记录
		List<Comment> replyList = dao.getReplyList(conn, orderBy, START_FROM_ZERO, REPLY_ROWS_TEN, parentId);

		//遍历获取到的回复记录，封装用户信息
		for (Comment reply : replyList) {
			//封装可能存在的回复对象
			Long targetId = reply.getTargetId();
			Comment targetComment = dao.getComment(conn, targetId);
			/*
			获取CommentBean
			（包装了用户信息、评论数据和其下的回复数据）
			 */
			CommentBean commentBean = CommentUtil.getCommentBean(conn, reply, targetComment, userid);
			//储存数据
			list.add(commentBean);
		}
		return list;
	}
}
