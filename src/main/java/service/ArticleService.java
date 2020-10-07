package service;

import common.dto.ResultState;
import pojo.po.Article;
import pojo.po.User;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public interface ArticleService<T extends Article> {
	/**
	 * 新发布博客
	 * @param article
	 * @return
	 */
	ResultState publishNewArtivle(Article article);
}
