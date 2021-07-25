package common.exception.specific;


import common.enums.ExceptionType;
import common.exception.BadRequestException;

/**
 * @author wtk
 * @description
 * @date 2021-05-28
 */
public class NotFoundException extends BadRequestException {
    public NotFoundException() {
        super(ExceptionType.NOT_FOUND, "目标不存在");
    }

    public NotFoundException(String message) {
        super(ExceptionType.NOT_FOUND, message);
    }
}
