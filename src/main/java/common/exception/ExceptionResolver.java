package common.exception;

import common.strategy.choose.ResponseChoose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pojo.dto.ResultState;

/**
 * @author wtk
 * @description
 * @date 2021-05-06
 */
@ControllerAdvice
@ResponseBody
public class ExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultState handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return ResponseChoose.notReadable("没有读取权限");
    }

    /**
     * 400 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParametersException.class)
    public ResultState handleException(ParametersException e) {
        logger.warn("参数异常", e);
        return ResponseChoose.exception("参数异常：" + e.getMsg());
    }

    /**
     * 500 其他未知异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultState handleException(Exception e) {
        if (e instanceof BusinessRuntimeException) {
            return ResponseChoose.error("接口参数异常" + (   (BusinessRuntimeException) e).getMsg());
        }

        logger.error("服务运行异常", e);
        return ResponseChoose.exception("服务器异常：" + e.getMessage());
    }
}