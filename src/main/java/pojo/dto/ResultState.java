package pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import common.enums.ResultType;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/15
 */
public class ResultState {

    @JSONField(ordinal = 0)
    private ResultType code;
    @JSONField(ordinal = 1)
    private String msg;

    public ResultState() {
    }

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

    @Override
    public String toString() {
        return "ResultState{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
