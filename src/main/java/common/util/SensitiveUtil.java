package common.util;

import filter.SensitiveFilter;
import pojo.po.Article;
import pojo.po.Posts;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/28
 */
public class SensitiveUtil {


	/**
	 * 过滤文章的敏感词
	 * @param article
	 */
	public static void filterArticle(Article article){
		/*
		TODO 优化 在获取数据时检查
		通过json获取数据，传入json检查敏感词
		 */
		SensitiveFilter filter = new SensitiveFilter();
		article.setContent(filter.checkString(article.getContent()));
		article.setTitle(filter.checkString(article.getTitle()));
		article.setLabel1(filter.checkString(article.getLabel1()));
		article.setLabel2(filter.checkString(article.getLabel2()));
		article.setLabel3(filter.checkString(article.getLabel3()));
		article.setLabel4(filter.checkString(article.getLabel4()));
		article.setLabel5(filter.checkString(article.getLabel5()));
	}

	/**
	 * 过滤文章的敏感词
	 * @param posts
	 */
	public static void filterPosts(Posts posts){
		/*
		TODO 优化 在获取数据时检查
		通过json获取数据，传入json检查敏感词
		 */
		SensitiveFilter filter = new SensitiveFilter();
		posts.setContent(filter.checkString(posts.getContent()));
		posts.setTitle(filter.checkString(posts.getTitle()));
		posts.setLabel1(filter.checkString(posts.getLabel1()));
		posts.setLabel2(filter.checkString(posts.getLabel2()));
		posts.setLabel3(filter.checkString(posts.getLabel3()));
		posts.setLabel4(filter.checkString(posts.getLabel4()));
		posts.setLabel5(filter.checkString(posts.getLabel5()));
	}
}
