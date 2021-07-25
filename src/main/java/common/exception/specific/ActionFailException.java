package common.exception.specific;


import common.enums.ExceptionType;
import common.exception.BadRequestException;

/**
 * @author wtk
 * @description 用户请求的行为失败
 * @date 2021-06-09
 */
public class ActionFailException extends BadRequestException {

    public ActionFailException(String message) {
        super(ExceptionType.ACTION_FAIL, message);
    }
}
