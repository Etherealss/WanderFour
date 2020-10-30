package service;

import common.enums.ResultType;
import pojo.dto.WritingBean;
import pojo.po.Writing;

import java.util.List;

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
	 * @throws Exception
	 */
	Long publishNewWriting(T t) throws Exception;

	/**
	 * 获取作品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WritingBean<T> getWriting(Long id) throws Exception;

	/**
	 * 获取作品列表
	 * @param partition
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<WritingBean<T>> getWritingList(int partition, String order) throws Exception;

	/**
	 * 获取简单的作品信息，仅包含作品id和标题
	 * @param partition
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<T> getSimpleWritingList(int partition, String order) throws Exception;
	/**
	 * 修改作品
	 * @param t
	 * @return
	 * @throws Exception
	 */
	ResultType updateWriting(T t) throws Exception;

	/**
	 * 删除作品
	 * @param writingId 作品编号
	 * @param deleterId 删除者账号
	 * @return
	 * @throws Exception
	 */
	ResultType deleteWriting(Long writingId, Long deleterId) throws Exception;
}
