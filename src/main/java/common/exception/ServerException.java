package common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 服务器异常
 * @date 2021-05-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends Exception {

    /** 结果码 */
    private int code;

    /** 结果码code的类型，与结果码一一对应 */
    private String type;

    /** 结果码的文字描述 */
    private String msg;

    public ServerException() {
    }

    public ServerException(String msg) {
        this.msg = msg;
    }

    public ServerException(int code) {
        this.code = code;
    }

    public ServerException(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }
}

