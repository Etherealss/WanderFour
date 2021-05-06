package common.exception;

/**
 * @author wtk
 * @description 交互参数异常
 * @date 2021-05-06
 */
public class ParametersException extends Exception {
    public ParametersException() {
    }

    public ParametersException(String message) {
        super(message);
    }

    public ParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
