package pojo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import pojo.po.Comment;

/**
 * @author 寒洲
 * @description 评论（回复）的中间类，
 * 添加了评论者的昵称和头像
 * 以及判断请求获取评论的用户是否为评论者
 * @date 2020/10/22
 */
public class CommentBean {

	/**
	 * 评论者昵称
	 */
	@JSONField(ordinal = 0)
	private String userNickname;

	/**
	 * 判断当前请求的用户是否可以删除该评论
	 * （是否为评论者）
	 */
	@JSONField(ordinal = 1)
	private boolean canDelete;

	/**
	 * 该评论/回复的具体信息
	 */
	@JSONField(ordinal = 2)
	private Comment comment;

	/**
	 * 评论者头像(base64)
	 */
	@JSONField(ordinal = 3)
	private String userImg;



	@Override
	public String toString() {
		//TODO 删除测试
		return "CommentBean{" +
				"comment=" + comment +
				", userNickname='" + userNickname + '\'' +
				", userImg='" +
				((userImg.length() >= 20) ? ("(注释)Base64数组前20位："+userImg.substring(0, 20) + "...'") : userImg) +
				'}';
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

//	public Comment getBeRepliedComment() {
//		return beRepliedComment;
//	}
//
//	public void setBeRepliedComment(Comment beRepliedComment) {
//		this.beRepliedComment = beRepliedComment;
//	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public CommentBean(Comment comment, String userImg, String userNickname) {
		this.comment = comment;
		this.userImg = userImg;
		this.userNickname = userNickname;
	}

	public CommentBean() {
	}
}
