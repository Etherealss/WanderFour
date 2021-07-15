package common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 用户操作异常
 * @date 2021-05-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends ServerException {

}
