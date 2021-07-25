package common.exception.specific;


import common.enums.ExceptionType;
import common.exception.BadRequestException;

/**
 * @author wtk
 * @description
 * @date 2021-06-15
 */
public class TypeErrorException extends BadRequestException {
    public TypeErrorException(String msg) {
        super(ExceptionType.ERROR_TYPE, msg);
    }
}
