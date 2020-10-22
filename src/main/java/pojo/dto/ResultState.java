package pojo.dto;

import common.enums.ResultType;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/15
 */
public class ResultState {

	private ResultType code;
	private String msg;

	public ResultState() {}

	public ResultState(ResultType code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultType getCode() {
		return code;
	}

	public void setCode(ResultType code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

//	public JSONObject getJson() {
//		JSONObject json = new JSONObject();
//		json.put("code", resultType);
//		json.put("msg", msg);
//		return json;
//	}
}