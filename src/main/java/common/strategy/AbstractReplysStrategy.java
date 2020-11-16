package common.strategy;

import common.factory.DaoFactory;
import common.util.CommentUtil;
import dao.CommentDao;
import dao.UserDao;
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
		Connection conn = vo.getConn();
		CommentDao commentDao = vo.getDao();
		Long parentId = vo.getParentId();
		Long userid = vo.getUserid();

		//获取回复的数据列表
		List<CommentBean> replysCommentBean = getReplysCommentBean(vo);

		//封装后添加到List<ReplyDto>中返回
		List<CommentDto> resultDtoList = new ArrayList<>();
		for (CommentBean replyBean : replysCommentBean) {

			// 回复数据
			Comment reply = replyBean.getComment();

			//只包装该回复记录，不添加其他replys
			//添加回复引用
			UserDao userDao = DaoFactory.getUserDAO();


			//判断当前回复是否需要引用
			CommentBean beRepliedBean = null;
			// 该记录(Comment)所回复的那个目标的id
			Long beRepliedId = reply.getTargetId();
			if (!beRepliedId.equals(reply.getParentId())){
				//该回复是回复另一条回复，则查询被回复的评论，添加引用(reply)
				//targetComment 意：复的那个记录的对象，用户添加引用
				Comment targetComment = commentDao.getComment(conn, beRepliedId);
				beRepliedBean = CommentUtil.getCommentBean(conn, userDao, targetComment, userid);
			}
			resultDtoList.add(new CommentDto(replyBean, beRepliedBean));
		}
		return resultDtoList;
	}

}
