package common.factory;

import common.util.ProxyUtil;
import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * @author 寒洲
 * @description Dao工厂
 * @date 2020/10/3
 */
public class DaoFactory {

	/**
	 * 用户DAO
	 * @return 用户DAO对象
	 */
	public static UserDao getUserDAO() {
		return new UserDaoImpl();
	}

}
