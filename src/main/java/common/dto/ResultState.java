package common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 寒洲
 * @description 结果码枚举
 * @date 2020/10/3
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultState {
	/** 成功 */
	SUCCESS("SUCCESS","成功"),
	/** 异常 */
	EXCEPTION("EXCEPTION", "出现异常"),

	/** 密码错误 */
	PW_ERROR("PW_ERROR", "密码错误"),
	/** 账号不存在 */
	USER_UN_FOUND("USER_UN_FOUND", "账号不存在"),
	/** 账号已登录 */
	LOGGED("LOGGED", "账号已登录"),


	;//这里加一个分号方便添加新枚举

	private final String state;
	/**
	 * 给前端的描述信息
	 */
	private final String msg;

	ResultState(String code, String msg){
		this.state = code;
		this.msg = msg;
	}

	public String toString(){
		return state + ":" + msg;
	}

	public String val(){
		return state;
	}

	public String  getState() {
		return state;
	}

	public String getMsg() {
		return msg;
	}
}
