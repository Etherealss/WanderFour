package common.exception;

/**
 * @author wtk
 * @description 用户信息异常
 * @date 2021-05-06
 */
public class UserException extends Exception {

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
