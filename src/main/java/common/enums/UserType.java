package common.enums;

/**
 * @author 寒洲
 * @description 用户类型枚举
 * @date 2020/10/10
 */
public enum UserType {
	/**
	 * 高中生
	 */
	SENIOR(1, "高中生"),
	/**
	 * 大学生
	 */
	COLLEGE(2, "大学生"),
	/**
	 * 教师
	 */
	TEACHER(3,"教师"),
	/**
	 * 其他用户
	 */
	OTHERS(4,"其他用户"),
	;

	private final int CODE;
	private final String VALUE;

	UserType(int code, String value){
		CODE = code;
		VALUE = value;
	}

	public int code() {
		return CODE;
	}

	public String val() {
		return VALUE;
	}

	/**
	 * 通过字符串获取数值
	 * @param value
	 * @return code
	 */
	public static int getCode(String value){
		for(UserType p : UserType.values()){
			if (p.val().equals(value)){
				return p.code();
			}
		}
		return -1;
	}

	/**
	 * 通过字符串获取枚举
	 * @param value
	 * @return
	 */
	public static UserType getPartition(String value){
		for(UserType p : UserType.values()){
			if (p.val().equals(value)){
				return p;
			}
		}
		return null;
	}
}
