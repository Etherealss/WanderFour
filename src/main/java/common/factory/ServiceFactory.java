package common.factory;

import common.util.ProxyUtil;
import pojo.po.Article;
import service.UserService;
import service.impl.ArticleServiceImpl;
import service.impl.UserServiceImpl;

/**
 * @author 寒洲
 * @description Service工厂
 * @date 2020/10/3
 */
public class ServiceFactory {

	/**
	 * 获取代理的用户Service
	 * @return 经过代理的用户Service对象
	 */
	public static UserService getUserService() {
		return ProxyUtil.getProxyForTransaction(new UserServiceImpl());
	}


	/**
	 * 获取代理的指定分区的文章Service
	 * @return 经过代理的文章Service对象
	 */
	public static ArticleServiceImpl<? extends Article> getArticleService() {
		return ProxyUtil.getProxyForTransaction(new ArticleServiceImpl<>());
	}
}
