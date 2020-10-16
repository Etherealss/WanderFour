package pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.annontation.Db;
import common.annontation.DbTable;
import common.enums.UserType;

import java.util.Date;

/**
 * @author 寒洲
 * @description 用户PO
 * @date 2020/10/2
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "user")
public class User {
	/**
	 * 邮箱作为ID
	 */
	private String userid;
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
	/**
	 * 被收藏数
	 */
	private Long collected;
	/**
	 * 注册时间
	 */
	private Date registerDate;

	private UserType userTypeEmun;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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
		this.userTypeEmun = UserType.getPartition(userType);
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

	public UserType getUserTypeEmun() {
		return userTypeEmun;
	}

	public void setUserTypeEmun(UserType userTypeEmun) {
		this.userTypeEmun = userTypeEmun;
	}

	public User() {}

	/**
	 * @param userid
	 * @param password
	 * @param nickname
	 * @param sex
	 * @param birthday
	 * @param avatarPath
	 * @param userType
	 * @param liked
	 * @param collected
	 * @param registerDate
	 */
	public User(String userid, String password, String nickname, Boolean sex, Date birthday, String avatarPath, String userType, Long liked, Long collected, Date registerDate) {
		this.userid = userid;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
		this.avatarPath = avatarPath;
		this.userType = userType;
		this.userTypeEmun = UserType.getPartition(userType);
		this.liked = liked;
		this.collected = collected;
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
		this.userid = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.avatarPath = avatarPath;
		this.userType = userType;
		this.userTypeEmun = UserType.getPartition(userType);
		this.registerDate = registerDate;
	}

	/**
	 * @param userid
	 * @param password
	 */
	public User(String userid, String password) {
		this.userid = userid;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + userid + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", sex=" + sex +
				", birthday=" + birthday +
				", avatarPath='" + avatarPath + '\'' +
				", userType='" + userType + '\'' +
				", liked=" + liked +
				", collected=" + collected +
				", registerDate=" + registerDate +
				'}';
	}
}
