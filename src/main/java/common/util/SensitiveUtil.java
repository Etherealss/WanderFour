package common.util;

import filter.SensitiveFilter;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description 敏感词
 * @date 2020/10/28
 */
public class SensitiveUtil {


	/**
	 * 过滤文章的敏感词
	 * @param filter
	 * @param writing
	 */
	public static<T extends Writing> void filterWriting(SensitiveFilter filter, T writing){
		writing.setContent(filter.checkString(writing.getContent()));
		writing.setTitle(filter.checkString(writing.getTitle()));
		writing.setLabel1(filter.checkString(writing.getLabel1()));
		writing.setLabel2(filter.checkString(writing.getLabel2()));
		writing.setLabel3(filter.checkString(writing.getLabel3()));
		writing.setLabel4(filter.checkString(writing.getLabel4()));
		writing.setLabel5(filter.checkString(writing.getLabel5()));
	}

	/**
	 * 过滤评论的敏感词
	 * @param comment
	 */
	public static void filterComment(SensitiveFilter filter, Comment comment){
		comment.setContent(filter.checkString(comment.getContent()));
	}

}
