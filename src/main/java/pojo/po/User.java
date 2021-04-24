package pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.enums.UserType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 寒洲
 * @description 用户PO
 * @date 2020/10/2
 */
public class User implements Serializable {
	private Long id;
	private String email;
	private String password;
	private String nickname;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * true为小哥哥，false为小姐姐
	 */
	private Boolean sex;
	private String avatarPath;
	private String userType;
	private Long liked;
	/** 被收藏数 */
	private Long collected;
	/** 注册时间 */
	private Date registerDate;
	private String school;
	private int major;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public String getUserType() {
		return userType;
	}

	/**
	 * 获取前端的参数时会使用
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getLiked() {
		return liked;
	}

	public void setLiked(Long liked) {
		this.liked = liked;
	}

	public Long getCollected() {
		return collected;
	}

	public void setCollected(Long collected) {
		this.collected = collected;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public User() {
	}

	/**
	 * @param email
	 * @param password
	 * @param nickname
	 * @param sex
	 * @param avatarPath
	 * @param userType
	 * @param registerDate
	 */
	public User(String email, String password, String nickname, Boolean sex,
	            String avatarPath, String userType, Date registerDate) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.avatarPath = avatarPath;
		this.userType = userType;
		this.registerDate = registerDate;
	}

	/**
	 * @param email
	 * @param password
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", userType=" + userType +
				", sex=" + sex +
				", birthday=" + birthday +
				", registerDate=" + registerDate +
				", liked=" + liked +
				", collected=" + collected +
				", school='" + school + '\'' +
				", major=" + major +
				", avatarPath='" +
				(avatarPath == null ? "null" : avatarPath.substring(0, 10) + "......" )
				+ '\'' +
				'}';
	}
}
