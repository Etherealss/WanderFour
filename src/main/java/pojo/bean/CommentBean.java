package pojo.bean;

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
	 * 该评论/回复的具体信息
	 */
	private Comment comment;

	/**
	 * 当前回复的引用
	 */
	private Comment relayComment;

	/**
	 * 评论者头像(base64)
	 */
	private String userImg;

	/**
	 * 评论者昵称
	 */
	private String userNickname;

	/**
	 * 判断当前请求的用户是否可以删除该评论
	 * （是否为评论者）
	 */
	private boolean canDelete;

	@Override
	public String toString() {
		return "CommentBean{" +
				"comment=" + comment +
				", userNickname='" + userNickname + '\'' +
				", userImg='" + userImg.substring(0,20) + "...'" +
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

	public Comment getRelayComment() {
		return relayComment;
	}

	public void setRelayComment(Comment relayComment) {
		this.relayComment = relayComment;
	}

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
