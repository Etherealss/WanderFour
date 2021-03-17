package common.strategy;

import common.factory.DaoFactory;
import common.util.CommentUtil;
import dao.CommentDao;
import dao.UserDao;
import pojo.vo.CommentVo;
import pojo.bean.CommentBean;
import pojo.po.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * 因为GetCommentsStrategy抽象类中的抽象方法为getComments，我们需要一个getReplys方法
 * 因此需要多定义一个GetLikesStrategy抽象类
 * 而我发现在获取评论和回复时都需要用到getReplysCommentBean方法
 * 所以我再次抽象一层，把getReplysCommentBean放在最外层
 * 这样获取评论和获取回复都可以用到这个方法
 * @date 2020/10/24
 */
public abstract class AbstractCommentAndReplyStrategy {

	/**
	 * 获取某一评论下的多条回复记录
	 * @param vo
	 * @return
	 * @throws SQLException
	 */
	protected List<CommentBean> getReplysCommentBean(CommentVo vo) throws SQLException {

		Connection conn = vo.getConn();
		CommentDao dao = vo.getDao();
		String orderBy = vo.getOrder();
		Long replyStart = vo.getReplyStart();
		int replyRows = vo.getReplyRows();
		Long parentId = vo.getParentId();
		Long userid = vo.getUserid();
		List<CommentBean> list = new ArrayList<>();

		//按需要的记录数 获取回复记录
		List<Comment> replyList = dao.getReplyList(orderBy, replyStart, replyRows, parentId);

		//遍历获取到的回复记录，封装用户信息
		for (Comment reply : replyList) {
			//判断是否添加回复的引用
			Comment targetComment = null;
//			if (addReplyReference) {
//				//封装可能存在的回复对象
//				Long targetId = reply.getTargetId();
//				//如果targetId为parentId，说明没有回复对象
//				if (!parentId.equals(targetId)) {
//					//如果两个Id不相同，说明targetId指向了一个回复对象，添加引用
//					targetComment = dao.getComment(conn, targetId);
//				}
//			}
			/*
			获取CommentBean
			（包装了用户信息、评论数据和其下的回复数据）
			 */
			UserDao userDao = DaoFactory.getUserDAO();
			CommentBean commentBean = CommentUtil.getCommentBean(conn, userDao, reply, userid);
			//储存数据
			list.add(commentBean);
		}
		return list;
	}
}
