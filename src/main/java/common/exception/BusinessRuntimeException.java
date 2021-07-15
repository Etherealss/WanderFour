package common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 业务异常
 * @date 2021-05-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessRuntimeException extends ServerRuntimeException {
}
