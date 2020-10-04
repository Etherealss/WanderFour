package common.bean;

import common.util.JdbcUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 寒洲
 * @description 基于动态代理实现异常时自动事务回滚
 * @date 2020/10/4
 */
public class ServiceTransactionProxyWrapper implements InvocationHandler {

	private Logger log = Logger.getLogger(ServiceTransactionProxyWrapper.class);
	/**
	 * 构造方法私有化，提供静态方法创建对象
	 * @param toBeProxy 要代理的事物对象
	 * @param <T>
	 * @return 代理对象
	 */
	public static <T> ServiceTransactionProxyWrapper create(T toBeProxy) {
		return new ServiceTransactionProxyWrapper(toBeProxy);
	}

	/** 要代理的对象 */
	private final Object serviceRunner;
	//构造方法私有化，传入要代理的事物对象
	private ServiceTransactionProxyWrapper(Object serviceRunner) {
		this.serviceRunner = serviceRunner;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.trace("invoke方法执行：method.getName() = " + method.getName());
		Object value;
		try{
			// 将受代理的事务执行
			// 创建并获取数据库连接，并设置为手动提交，开启事务
			JdbcUtil.beginTransaction();
			// 执行对应service方法，在service中会再次获取上面创建的连接
			// 获取service执行的结果
			value = method.invoke(serviceRunner, args);
		}catch (Exception ex) {
			//事务回滚
			log.error("事务执行出现异常了！回滚！");
			JdbcUtil.rollbackTransaction();
			throw ex;
		}finally {
			// 事务提交
			JdbcUtil.commitTransaction();
			// 事务完毕，结束事务
			JdbcUtil.closeTransaction();
		}
		return value;
	}
}
