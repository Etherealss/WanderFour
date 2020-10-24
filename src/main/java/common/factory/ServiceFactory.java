package common.factory;

import common.util.ProxyUtil;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;
import service.*;
import service.impl.*;

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
		return (T)ProxyUtil.getProxyForTransaction(new PostsServiceImpl());
	}

	/**
	 * 获取代理的指定分区的文章Service
	 * @return 经过代理的文章Service对象
	 */
	public static <T> T getWritingService(Class<T> clazz) throws IllegalAccessException, InstantiationException {
		return ProxyUtil.getProxyForTransaction(clazz);
	}

	/**
	 * 获取经过代理的点赞Service
	 * @return
	 */
	public static LikeService getLikeService(){
		return ProxyUtil.getProxyForTransaction(new LikeServiceImpl());
	}

	/**
	 * 获取经过代理的分类Service
	 * @return
	 */
	public static CategoryService getCategoryService(){
		return ProxyUtil.getProxyForTransaction(new CategoryServiceImpl());
	}

	/**
	 * 获取经过代理的评论Service
	 * @return
	 */
	public static CommentService getCommentService(Class<? extends Writing> clazz){
		return ProxyUtil.getProxyForTransaction(new CommentServiceImpl(clazz));
	}

//	/**
//	 * 获取代理的指定分区的文章Service
//	 * @return 经过代理的文章Service对象
//	 */
//	public static ArticleServiceImpl<CollegeArticle> getCollegeArticleService() {
//		return ProxyUtil.getProxyForTransaction(new ArticleServiceImpl<>());
//	}

	//	/**
//	 * 获取代理的指定分区的文章Service
//	 * @return 经过代理的文章Service对象
//	 */
//	public static <T> T getWritingService(T t) {
//		return (T)ProxyUtil.getProxyForTransaction(t);
//	}
}
