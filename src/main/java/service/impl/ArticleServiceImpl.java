package service.impl;

import common.dto.ResultState;
import pojo.po.Article;
import service.ArticleService;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class ArticleServiceImpl<T extends Article> implements ArticleService<T> {
	@Override
	public ResultState publishNewArtivle(Article article) {
		return null;
	}
}
