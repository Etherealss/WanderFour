package common.factory;

import service.UserService;
import service.impl.UserServiceImpl;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/3
 */
public class ServiceFactory {

	public static UserService getUserService() {
		return new UserServiceImpl();
	}
}
