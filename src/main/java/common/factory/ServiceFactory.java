package common.factory;

import common.util.ProxyUtil;
import pojo.po.Writing;
import service.UserService;
import service.WritingService;
import service.impl.ArticleServiceImpl;
import service.impl.UserServiceImpl;

/**
 * @author 寒洲
 * @description Service工厂
 * @date 2020/10/3
 */
public class ServiceFactory {

	/**
	 * 获取代理后的用户Service
	 * @return 经过代理的用户Service对象
	 */
	public static UserService getUserService() {
		return ProxyUtil.getProxyForTransaction(new UserServiceImpl());
	}

	/**
	 * 获取代理后的文章Service
	 * @param <T>
	 * @return 经过代理的文章Service
	 */
	public static <T> T getArticleService() {
		return (T)ProxyUtil.getProxyForTransaction(new ArticleServiceImpl());
	}

	/**
	 * 获取代理后的帖子Service
	 * @param <T>
	 * @return 经过代理的帖子Service
	 */
	public static <T> T getPostsService() {
//		return (T)ProxyUtil.getProxyForTransaction(new PostsServiceImpl());
		return null;
	}

//	/**
//	 * 获取代理的指定分区的文章Service
//	 * @return 经过代理的文章Service对象
//	 */
//	public static <T> T getWritingService(T t) {
//		return (T)ProxyUtil.getProxyForTransaction(t);
//	}

	/**
	 * 获取代理的指定分区的文章Service
	 * @return 经过代理的文章Service对象
	 */
	public static <T> T getWritingService(Class<T> clazz) throws IllegalAccessException, InstantiationException {
		return ProxyUtil.getProxyForTransaction(clazz);
	}

//	/**
//	 * 获取代理的指定分区的文章Service
//	 * @return 经过代理的文章Service对象
//	 */
//	public static ArticleServiceImpl<CollegeArticle> getCollegeArticleService() {
//		return ProxyUtil.getProxyForTransaction(new ArticleServiceImpl<>());
//	}
}
