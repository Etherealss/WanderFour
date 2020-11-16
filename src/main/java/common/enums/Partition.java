package common.enums;

/**
 * @author 寒洲
 * @description 三个大分区
 * @date 2020/10/7
 */
public enum  Partition {
	/** 学习天地 */
	LEARNING(1, "learning"),
	/** 专业介绍 */
	MAJOR(2, "major"),
	/** 大学风采 */
	COLLEGE(3,"college"),
	;

	private final int CODE;
	private final String VALUE;

	Partition(int code, String value){
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
		for(Partition p : Partition.values()){
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
	public static Partition getPartition(String value){
		for(Partition p : Partition.values()){
			if (p.val().equals(value)){
				return p;
			}
		}
		return null;
	}

	public static Partition[] getAllPartition(){
		return new Partition[] {LEARNING, MAJOR, COLLEGE};
	}
}
