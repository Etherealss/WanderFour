package common.util;

import common.bean.ServiceTransactionProxyWrapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/4
 */

public class ProxyUtil {

	/**
	 * 获取代理后的对象
	 *
	 * @param clazz        类对象
	 * @param proxyWrapper 包裹执行对象的实现接口
	 * @param <T>          泛型
	 *
	 * @return 创建一个被代理后的对象
	 */
	private static <T> T getInstance(Class<T> clazz, InvocationHandler proxyWrapper) {
		//JDK动态代理
		return (T) Proxy.newProxyInstance(
				clazz.getClassLoader(),
				clazz.getInterfaces(),
				proxyWrapper
		);
	}

	/**
	 * 获取事物的代理对象
	 * @param toBeProxy 要代理的对象
	 * @param <T>
	 * @return 被代理后的对象
	 */
	public static <T> T getProxyForTransaction(T toBeProxy) {
		// 传入要代理的对象，获取被代理后的对象
		return (T) getInstance(toBeProxy.getClass(),
				ServiceTransactionProxyWrapper.create(toBeProxy));
	}
}
