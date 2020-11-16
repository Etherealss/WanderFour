package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.ReplyChoose;
import common.strategy.impl.comment.*;
import common.util.JdbcUtil;
import dao.CommentDao;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.po.Comment;
import pojo.po.Writing;
import service.CommentService;

import java.sql.Connection;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/23
 */
public class CommentServiceImpl implements CommentService {

	private Logger logger = Logger.getLogger(CommentServiceImpl.class);

	private static final String ORDER_BY_LIKE = "like";
	private static final String ORDER_BY_TIME = "time";

	private CommentDao dao;

	/**
	 * @param clazz
	 */
	public CommentServiceImpl(Class<? extends Writing> clazz) {
		//根据泛型获取对应数据库表的DAO
		dao = DaoFactory.getCommentDao(clazz);
	}

	@Override
	public List<CommentDto> getHotCommentList(Long parentId, Long userId) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		//选择策略 按点赞数获取热门评论及其回复
		CommentChoose choose = new CommentChoose(new GetHeadCommentsAndReplyByLike());
		//获取评论DtoList
		CommentVo vo = new CommentVo();
		vo.setParentId(parentId);
		vo.setUserid(userId);
		vo.setConn(conn);
		vo.setDao(dao);
		//策略 获取所有评论数据
		List<CommentDto> list = choose.doGet(vo);
		return list;
	}

	@Override
	public PageBean<CommentDto> getCommentListByPage(CommentVo vo, int currentPage) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		vo.setConn(conn);
		vo.setDao(dao);

		PageBean<CommentDto> pb;

		if (vo.getTargetId() == null) {
			//要获取评论

			//TODO 此处按道理应该在策略中确定
			int commentRows = DaoEnum.COMMENT_ROWS_TEN;
			int replyRows = DaoEnum.REPLY_ROWS_THREE;
			vo.setCommentRows(commentRows);
			vo.setReplyRows(replyRows);
			Long parentId = vo.getParentId();
			//存入当前页码和每页显示的记录数
			pb = new PageBean<>(currentPage, commentRows);
			//获取并存入总记录数
			Long totalCount = dao.countCommentByParentId(conn, parentId);
			pb.setTotalCount(totalCount);
			//计算索引 注意在 -1 的时候加入long类型，使结果升格为Long
			Long start = (currentPage - 1L) * commentRows;
			vo.setCommentStart(start);
			logger.debug("获取评论：vo = " + vo);

			//策略选择
			CommentChoose choose;
			if (vo.getOrder().equals(DaoEnum.ORDER_BY_LIKE)) {
				//按点赞获取
				choose = new CommentChoose(new GetCommentsByLike());
			} else if (vo.getOrder().equals(DaoEnum.ORDER_BY_TIME)) {
				//按时间获取
				choose = new CommentChoose(new GetCommentsByTime());
			} else {
				throw new Exception("OrderBy参数错误：orberBy = " + vo.getOrder());
			}
			//执行策略
			List<CommentDto> list = choose.doGet(vo);
			pb.setList(list);
			/*
			计算总页码数
			如果总记录数可以整除以每页显示的记录数，
			那么总页数就是它们的商否则
			说明有几条数据要另开一页显示，总页数+1
			 */
			int page = (int) (totalCount / commentRows);
			int totalPage = totalCount % commentRows == 0 ? page : page + 1;
			pb.setTotalPage(totalPage);
		} else {
			//要获取回复

			//TODO 此处按道理应该在策略中确定
			int replyRows = DaoEnum.REPLY_ROWS_TEN;
			vo.setReplyRows(replyRows);
			Long parentId = vo.getParentId();
			Long targetId = vo.getTargetId();
			//存入当前页码和每页显示的记录数
			pb = new PageBean<>(currentPage, replyRows);

			//获取并存入总回复记录数
			Long totalCount = dao.countReplyByParentId(conn, parentId);
			pb.setTotalCount(totalCount);

			//计算索引 注意在 -1 的时候加入long类型，使结果升格为Long
			Long start = (currentPage - 1L) * replyRows;
			vo.setReplyStart(start);
			logger.debug("获取回复，vo = " + vo);

			//策略选择
			ReplyChoose choose;
			if (vo.getOrder().equals(DaoEnum.ORDER_BY_LIKE)) {
				//按点赞获取
				choose = new ReplyChoose(new GetReplysByLike());
			} else if (vo.getOrder().equals(DaoEnum.ORDER_BY_TIME)) {
				//按时间获取
				choose = new ReplyChoose(new GetReplysByTime());
			} else {
				throw new Exception("OrderBy参数错误：orberBy = " + vo.getOrder());
			}
			//执行策略
			List<CommentDto> list = choose.doGet(vo);
			pb.setList(list);
			/*
			计算总页码数
			如果总记录数可以整除以每页显示的记录数，
			那么总页数就是它们的商否则
			说明有几条数据要另开一页显示，总页数+1
			 */
			int page = (int) (totalCount / replyRows);
			int totalPage = totalCount % replyRows == 0 ? page : page + 1;
			pb.setTotalPage(totalPage);
		}
		return pb;
	}

	@Override
	public ResultType publishNewComment(Comment comment) throws Exception {
		Connection conn  = JdbcUtil.getConnection();
		boolean success;
		if (comment.getTargetId() == null) {
			//评论
			success = dao.createNewComment(conn, comment);
		} else {
			//回复
			success = dao.createNewReply(conn, comment);
		}
		if (success){
			return ResultType.SUCCESS;
		}
		return ResultType.EXCEPTION;
	}

	@Override
	public ResultType deleteComment(Long commentId, Long userid) throws Exception {
		Connection conn  = JdbcUtil.getConnection();
		Long commentUserId = dao.getCommentUserId(conn, userid);
		if (!commentId.equals(commentUserId)){
			return ResultType.NOT_AUTHOR;
		}
		boolean success = dao.deleteComment(conn, commentId);
		if (success){
			return ResultType.SUCCESS;
		}
		return ResultType.EXCEPTION;
	}

}
