package common.factory;

import common.enums.TargetType;
import dao.CategoryDao;
import dao.LikeDao;
import dao.WritingDao;
import dao.UserDao;
import dao.impl.*;
import pojo.po.Article;
import pojo.po.Posts;

/**
 * @author 寒洲
 * @description DAO工厂
 * @date 2020/10/3
 */
public class DaoFactory {

	/**
	 * 用户DAO
	 *
	 * @return 用户DAO对象
	 */
	public static UserDao getUserDAO() {
		return new UserDaoImpl();
	}

	/**
	 * 获取各分区文章的DAO
	 * @return
	 */
	public static WritingDao<Article> getArticleDao() {
		return new ArticleDaoImpl();
	}

	/**
	 * 获取各分区问贴的DAO
	 * @return
	 */
	public static WritingDao<Posts> getPostsDao() {
		return new PostsDaoImpl();
	}

	/**
	 * 获取点赞DAO
	 * @return
	 */
	public static LikeDao getLikeDao(TargetType type) {
		return new LikeDaoImpl(type);
	}

	/**
	 * 获取分类DAO
	 * @return
	 */
	public static CategoryDao getCategoryDao() {
		return new CategoryDaoImpl();
	}

}
