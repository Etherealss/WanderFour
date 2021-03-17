package common.strategy;

import common.enums.DaoEnum;
import common.factory.DaoFactory;
import common.util.CommentUtil;
import dao.CommentDao;
import dao.UserDao;
import org.apache.log4j.Logger;
import pojo.vo.CommentVo;
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
		CommentDao commentDao = vo.getDao();
		Connection conn = vo.getConn();
		String order = vo.getOrder();
		Long parentId = vo.getParentId();
		Long userid = vo.getUserid();

		//结果列表
		List<CommentDto> returnDtoList = new ArrayList<>();

		//获取rows条 顶层评论 数据
		List<Comment> commentList = commentDao.getCommentList(
				order, vo.getCommentStart(), vo.getCommentRows(), parentId);

		/*
		加工评论数据：
		1、封装CommentBean，包括评论信息、评论用户昵称和头像
		2、封装回复的评论信息
			2.1封装回复的评论的CommentBean
		3、判断是否要添加引用
			2.1、需要添加引用：获取并添加引用targetBean
			2.2、不需要添加，则进行下一步
		3、将commentBean, [replysCommentBean]，[targetBean]封装到Dto，添加到要返回的list中
		 */
		for (Comment comment : commentList) {
			Long targetId = comment.getTargetId();

			assert comment.getParentId() != null;
			assert targetId != null;

			/*
			包装 顶层评论的Bean
			 */
			// 封装回复的评论的CommentBean
			UserDao userDao = DaoFactory.getUserDAO();
			CommentBean commentBean = CommentUtil.getCommentBean(conn, userDao, comment, userid);

			CommentDto resultDto = null;
			//判断是否获取回复列表（评论才有回复列表，回复列表就显示在回复没有回复列表
			if (vo.getReplyRows() != 0) {
				/*
				要获取该评论的回复记录信息列表
				*/
				CommentVo voForReply = vo;
				voForReply.setOrder(DaoEnum.FIELD_ORDER_BY_LIKE);
				List<CommentBean> replysCommentBeanList = getReplysCommentBean(voForReply);

				// 获取回复总数
				int count = commentDao.countReplyByParentId(parentId).intValue();
				//创建评论的Dto，封装数据
				resultDto = new CommentDto(commentBean, replysCommentBeanList, count);
			} else {

				// 获取的是评论下的【回复列表】
				/*
				1、parentId和targetId不相同，说明该评论是回复其他评论
				则进入if语句，查询回复的评论，添加引用(reply)
				2、parentId和targetId相同，说明该回复是回复评论，不引用
				获取的targetComment为null，不影响封装
				 */
				CommentBean targetBean = null;
				// parentId和targetId不为null
				if (!comment.getParentId().equals(targetId)) {
					//该回复是回复另一条回复，则查询被回复的评论，添加引用(reply)
					//targetComment 意：复的那个记录的对象，用户添加引用

					Comment targetComment = commentDao.getComment(targetId);
					targetBean = CommentUtil.getCommentBean(conn, userDao, targetComment, userid);
				}
				// 回复的Dto，封装数据
				resultDto = new CommentDto(commentBean, targetBean);
			}
			returnDtoList.add(resultDto);
		}
		return returnDtoList;
	}
}
