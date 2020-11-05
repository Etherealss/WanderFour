package common.util;

import common.factory.DaoFactory;
import dao.UserDao;
import org.apache.log4j.Logger;
import pojo.bean.CommentBean;
import pojo.po.Comment;
import pojo.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 寒洲
 * @description 评论工具类
 * @date 2020/10/23
 */
public class CommentUtil {

	private static Logger logger = Logger.getLogger(CommentUtil.class);

	/**
	 * 包装评论者的用户昵称和头像（base64转码）
	 * @param conn
	 * @param comment 当前评论的Comment对象
	 * @param reply   回复对象的Comment对象
	 * @param userid  当前用户
	 * @return 包括评论信息、评论者昵称、评论者头像的CommentBean
	 * @throws SQLException
	 */
	public static CommentBean getCommentBean(Connection conn, Comment comment,
	                                         Comment reply, Long userid) throws SQLException {
		CommentBean cb = new CommentBean();
		//判断是否为当前用户
		Long reviewerUserId = comment.getUserid();
		//userid可以为null
		cb.setCanDelete(reviewerUserId.equals(userid));
		//封装回复数据，如果没有，则赋值为null，不影响
		cb.setRelayComment(reply);
		//获取评论者的用户信息
		UserDao userDao = DaoFactory.getUserDAO();
		/*
		注意！函数参数的userid是当前用户的userid，是用来判断评论能否删除的
		而comment的userid是评论者的userid，此处要封装评论者的用户信息（reviewerInfo）
		需要用到的是reviewerUserId
		 */
		User reviewerInfo = userDao.getImgAndNicknameById(conn, reviewerUserId);
		cb.setUserNickname(reviewerInfo.getNickname());
		//用户头像
		//使用base64转码
		byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
		String imgByBase64 = FileUtil.getImgByBase64(imgStream);
		//TODO 评论图片转码储存
		cb.setUserImg("imgByBase64");
		//封装评论信息
		cb.setComment(comment);
		return cb;
	}

}
