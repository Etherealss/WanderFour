package pojo.bean;

import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class WritingBean<T extends Writing> {
	/** 作者昵称 */
	private String userNickname;

	/** 判断用户是不是作者 */
	private boolean isAuthor;
	/** 判断用户是否已点赞 */
	private boolean isLiked;
	/** 判断用户是否已收藏 */
	private boolean isCollected;
	/** 作者头像 */
	private String userImg;
	/** 作品对象 */
	private T writing;

	public T getWriting() {
		return writing;
	}

	public void setWriting(T writing) {
		this.writing = writing;
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

	public boolean isAuthor() {
		return isAuthor;
	}

	public void setAuthor(boolean author) {
		isAuthor = author;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean liked) {
		this.isLiked = liked;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean collected) {
		this.isCollected = collected;
	}

	public WritingBean(String userNickname, boolean isAuthor, boolean isLiked, boolean isCollected, String userImg, T writing) {
		this.userNickname = userNickname;
		this.isAuthor = isAuthor;
		this.isLiked = isLiked;
		this.isCollected = isCollected;
		this.userImg = userImg;
		this.writing = writing;
	}

	/**
	 * @param writing
	 * @param userNickname
	 * @param userImg
	 */
	public WritingBean(T writing, String userNickname, String userImg) {
		this.writing = writing;
		this.userImg = userImg;
		this.userNickname = userNickname;
	}

	public WritingBean() {
	}

	@Override
	public String toString() {
		return "WritingBean{" +
				"userNickname='" + userNickname + '\'' +
				", isAuthor=" + isAuthor +
				", canLike=" + isLiked +
				", canCollect=" + isCollected +
				", userImg='" + userImg + '\'' +
				", writing=" + writing +
				'}';
	}
}
