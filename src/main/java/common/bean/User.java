package common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author 寒洲
 * @description 用户Bean
 * @date 2020/10/2
 */
public class User {
	private String email;
	private String password;
	private String nickname;
	@JsonFormat(pattern = "yyyy-MM-dd")
	// true为小哥哥，false为小姐姐
	private Boolean sex;
	private Date birthday;
	private String avatarPath;
	private String userType;
	// 获赞数
	private int beLiked;
	// 被收藏数
	private int beCollected;
	// 注册时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date registerDate;

	public User() {
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

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getBeLiked() {
		return beLiked;
	}

	public void setBeLiked(int beLiked) {
		this.beLiked = beLiked;
	}

	public int getBeCollected() {
		return beCollected;
	}

	public void setBeCollected(int beCollected) {
		this.beCollected = beCollected;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @param email
	 * @param password
	 * @param nickname
	 * @param sex
	 * @param birthday
	 * @param avatarPath
	 * @param userType
	 * @param beLiked
	 * @param beCollected
	 * @param registerDate
	 */
	public User(String email, String password, String nickname, Boolean sex, Date birthday, String avatarPath, String userType, int beLiked, int beCollected, Date registerDate) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
		this.avatarPath = avatarPath;
		this.userType = userType;
		this.beLiked = beLiked;
		this.beCollected = beCollected;
		this.registerDate = registerDate;
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
	public User(String email, String password, String nickname, Boolean sex, String avatarPath, String userType, Date registerDate) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
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
				"email='" + email + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", sex=" + sex +
				", birthday=" + birthday +
				", avatarPath='" + avatarPath + '\'' +
				", userType='" + userType + '\'' +
				", beLiked=" + beLiked +
				", beCollected=" + beCollected +
				", registerDate=" + registerDate +
				'}';
	}
}
