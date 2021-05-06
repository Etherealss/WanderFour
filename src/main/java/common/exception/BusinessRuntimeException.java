package common.exception;

/**
 * @author wtk
 * @description 业务异常
 * @date 2021-05-06
 */
public class BusinessRuntimeException extends RuntimeException {

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果码描述
     */
    private String msg;

    public BusinessRuntimeException() {
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
