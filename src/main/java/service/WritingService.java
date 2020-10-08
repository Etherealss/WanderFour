package service;

import common.dto.ResultState;
import pojo.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public interface WritingService<T extends Writing> {
	/**
	 * 新发布博客
	 * @param writing
	 * @return
	 */
	ResultState publishNewWriting(Writing writing);
}
