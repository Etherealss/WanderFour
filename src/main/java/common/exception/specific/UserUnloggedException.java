package common.exception.specific;


import common.exception.BadRequestException;

/**
 * @author wtk
 * @description
 * @date 2021-05-28
 */
public class UserUnloggedException extends BadRequestException {

    public UserUnloggedException() {
        super("UserUnlogged", "用户未登录");
    }
}
