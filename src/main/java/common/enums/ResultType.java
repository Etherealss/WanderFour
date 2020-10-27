package common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 寒洲
 * @description 结果码枚举
 * @date 2020/10/3
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultType {
	/** 成功 */
	SUCCESS("SUCCESS"),
	/** 异常 */
	EXCEPTION("EXCEPTION"),
	/** 接口错误 */
	ERROR("ERROR"),

	/** 密码错误 */
	PW_ERROR("PW_ERROR"),
	/** 账号不存在 */
	USER_UN_FOUND("USER_UN_FOUND"),
	/** 账号已登录 */
	LOGGED("LOGGED"),

	IS_REGISTED("IS_REGISTED"),

	NOT_AUTHOR("NOT_AUTHOR"),

	/**已点赞*/
	HAVE_LIKED("HAS_LIKED"),
	HAVE_NOT_LIKED("HAVE_NOT_LIKED"),

	/**没有记录*/
	NO_RECORD("NO_RECORD"),
	;

	private final String code;

	ResultType(String code) {
		this.code = code;
	}

	public String val() {
		return code;
	}

	public String getCode() {
		return code;
	}
}
