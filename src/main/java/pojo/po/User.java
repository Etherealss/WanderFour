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
	private UserType userType;
	private String userTypeStr;
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

	public UserType getUserType() {
		return userType;
	}

	/**
	 * 获取前端的参数时会使用
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = UserType.getPartition(userType);
	}

	/**
	 * 获取数据库数据时会使用
	 * @param userTypeStr
	 */
	public void setUserTypeStr(String userTypeStr){
		this.userType = UserType.getPartition(userTypeStr);
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

	public User() {}

	public User(Long id, String email, String password, String nickname, Date birthday,
	            Boolean sex, String avatarPath, String userType, Long liked, Long collected,
	            Date registerDate) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.birthday = birthday;
		this.sex = sex;
		this.avatarPath = avatarPath;
		this.userType = UserType.getPartition(userType);
		this.liked = liked;
		this.collected = collected;
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
	 * @param liked
	 * @param collected
	 * @param registerDate
	 */
	public User(String email, String password, String nickname, Boolean sex, Date birthday,
	            String avatarPath, String userType, Long liked, Long collected, Date registerDate) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
		this.avatarPath = avatarPath;
		this.userType = UserType.getPartition(userType);
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
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.avatarPath = avatarPath;
		this.userType = UserType.getPartition(userType);
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
				", nickname='" + nickname + '\'' +
				", sex=" + sex +
				", school='" + school + '\'' +
				", liked=" + liked +
				", major=" + major +
				", userType=" + userType +
				", userTypeStr='" + userTypeStr + '\'' +
				", collected=" + collected +
				", birthday=" + birthday +
				", password='" + password + '\'' +
				", registerDate=" + registerDate +
				", avatarPath='" + avatarPath + '\'' +
				'}';
	}
}
