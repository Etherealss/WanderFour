package pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.annontation.Db;
import common.annontation.DbField;
import common.annontation.DbFieldId;
import common.annontation.DbTable;

import java.util.Date;

/**
 * @author 寒洲
 * @description 用户PO
 * @date 2020/10/2
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "user")
public class User {
	@DbFieldId
	@DbField("email")
	private String userid; // 用邮箱作为id
	@DbField("u_password")
	private String password;
	@DbField("nickname")
	private String nickname;
	@DbField("birthday")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@DbField("sex")
	private Boolean sex; // true为小哥哥，false为小姐姐
	@DbField("avatar")
	private String avatarPath;
	@DbField("u_type")
	private String userType;
	@DbField("u_liked")
	private int liked; // 获赞数
	@DbField("u_collected")
	private int collected; // 被收藏数
	@DbField("register_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date registerDate; // 注册时间

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
	}

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}

	public int getCollected() {
		return collected;
	}

	public void setCollected(int collected) {
		this.collected = collected;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
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
	public User(String userid, String password, String nickname, Boolean sex, Date birthday, String avatarPath, String userType, int liked, int collected, Date registerDate) {
		this.userid = userid;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
		this.avatarPath = avatarPath;
		this.userType = userType;
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
