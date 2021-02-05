package common.factory;

import common.enums.TargetType;
import dao.*;
import dao.impl.*;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description DAO工厂
 * @date 2020/10/3
 */
public class DaoFactory {

	/**
	 * 好友列表
	 * @return
	 */
	public static FriendRelationDao getFriendRelationDao(){
		return new FriendRelationDaoImpl();
	}

	/**
	 * 用户DAO
	 *
	 * @return 用户DAO对象
	 */
	public static UserDao getUserDAO() {
		return new UserDaoImpl();
	}

	/**
	 * 获取文章的DAO
	 * @return
	 */
	public static WritingDao<Article> getArticleDao() {
		return new ArticleDaoImpl();
	}

	/**
	 * 获取问贴的DAO
	 * @return
	 */
	public static WritingDao<Posts> getPostsDao() {
		return new PostsDaoImpl();
	}

	/**
	 * 获取点赞DAO
	 * @param type 请求的表类型
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


	/**
	 * 按表获取评论DAO
	 * @param clazz 确定评论表
	 * @return
	 */
	public static CommentDao getCommentDao(Class<? extends Writing> clazz){
		try {
			return new CommentDaoImpl(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取敏感词DAO
	 * @return
	 */
	public static SensitiveDao getSensitiveDao(){
		return new SensitiveDaoImpl();
	}

	/**
	 * 便利贴DAO
	 * @return
	 */
	public static StickyNoteDao getStickyNoteDao(){
		return new StickyNoteDaoImpl();
	}
}
