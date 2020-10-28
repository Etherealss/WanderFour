package pojo.dto;

import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class WritingBean {
	/** 作品对象 */
	private Writing writing;
	/** 作者头像 */
	private String userImg;
	/** 作者昵称 */
	private String userNickname;

	public Writing getWriting() {
		return writing;
	}

	public void setWriting(Writing writing) {
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
	public WritingBean(Writing writing, String userNickname, String userImg) {
		this.writing = writing;
		this.userImg = userImg;
		this.userNickname = userNickname;
	}

	public WritingBean() {
	}
}
