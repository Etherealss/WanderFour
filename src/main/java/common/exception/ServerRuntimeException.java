package common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description
 * @date 2021-05-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerRuntimeException extends RuntimeException{

    /** 结果码 */
    private int code;

    /** 结果码code的类型，与结果码一一对应 */
    private String type;

    /** 结果码的文字描述 */
    private String msg;

    public ServerRuntimeException() {
    }

    public ServerRuntimeException(String msg) {
        this.msg = msg;
    }

    public ServerRuntimeException(int code) {
        this.code = code;
    }

    public ServerRuntimeException(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }
}
