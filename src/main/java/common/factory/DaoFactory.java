package common.factory;

import dao.WritingDao;
import dao.UserDao;
import dao.impl.ArticleDaoImpl;
import dao.impl.UserDaoImpl;
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

//	public static WritingDao<Posts> getPostsDao() {
//		return new PostsDaoImpl();
//	}
}
