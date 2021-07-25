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

    /** 异常类型 */
    private String type;

    /** 结果码的文字描述 */
    private String msg;

    public ServerException() {
    }

    public ServerException(String msg) {
        this.msg = msg;
    }

    public ServerException(String type, String msg) {
        super(msg);
        this.type = type;
        this.msg = msg;
    }
}

