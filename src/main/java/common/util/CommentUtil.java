package common.util;

import dao.UserDao;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import pojo.bean.CommentBean;
import pojo.po.Comment;
import pojo.po.User;

import java.io.IOException;
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
	 * @param userDao
	 * @param comment 当前评论的Comment对象
	 * @param userid  当前用户
	 * @return 包括评论信息、评论者昵称、评论者头像的CommentBean
	 * @throws SQLException
	 */
	public static CommentBean getCommentBean(@NotNull UserDao userDao, @NotNull Comment comment,
	                                         Long userid) {
		CommentBean cb = new CommentBean();
		//判断是否为当前用户
		Long reviewerUserId = comment.getUserid();
		//userid可以为null
		cb.setCanDelete(reviewerUserId.equals(userid));


		//获取评论者的用户信息
		/*
		注意！函数参数的userid是当前用户的userid，是用来判断评论能否删除的
		而comment的userid是评论者的userid，此处要封装评论者的用户信息（reviewerInfo）
		需要用到的是reviewerUserId
		 */
		User reviewerInfo = userDao.getImgAndNicknameById(reviewerUserId);
		cb.setUserNickname(reviewerInfo.getNickname());
		//用户头像
		//使用base64转码
		byte[] imgStream;
		try {
			imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
			String imgByBase64 = FileUtil.getImgByBase64(imgStream);
			cb.setUserImg(imgByBase64);
		} catch (IOException e) {
			logger.error("评论图片转码异常", e);
		}
		//封装评论信息
		cb.setComment(comment);
		return cb;
	}

}
