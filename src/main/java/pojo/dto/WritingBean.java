package pojo.dto;

import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class WritingBean<T extends Writing> {
	/** 作者昵称 */
	private String userNickname;
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
				", userImg='" + userImg + '\'' +
				", writing=" + writing +
				'}';
	}
}
