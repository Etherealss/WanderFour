package common.factory;

import dao.ArticleDao;
import dao.UserDao;
import dao.impl.ArticleDaoImpl;
import dao.impl.UserDaoImpl;
import pojo.po.Article;

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
	 *
	 * @param <T>  指定分区的文章DAO类型
	 *
	 * @return 各分区文章的DAO
	 */
	public static <T> ArticleDao<? extends Article> getArticleDao() {
		return new ArticleDaoImpl<>();
	}

}
