package service;

import common.enums.ResultType;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public interface WritingService<T extends Writing> {
	/**
	 * 发布新作品
	 * @param t
	 * @return
	 */
	ResultType publishNewWriting(T t);

	/**
	 * 获取作品
	 * @param id
	 * @return
	 */
	T getWriting(Long id);

	/**
	 * 修改作品
	 * @param t
	 * @return
	 */
	ResultType updateWriting(T t);

	/**
	 * 删除作品
	 * @param writingId 作品编号
	 * @param deleterId 删除者账号
	 * @return
	 */
	ResultType deleteWriting(Long writingId, Long deleterId);
}
