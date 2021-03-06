package service;

import common.enums.ResultType;
import pojo.bean.WritingBean;
import pojo.bo.EsBo;
import pojo.dto.WritingDto;
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
	Long publishNewWriting(T t);

	/**
	 * 获取作品
	 * @param writingId 作品id
	 * @param userid 可以为null，用户判断是否为作者
	 * @return
	 * @throws Exception
	 */
	WritingBean<T> getWritingBean(Long writingId, Long userid);

	/**
	 * 获取作品列表
	 *
	 * @param userid
	 * @param partition
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<WritingDto<T>> getWritingList(Long userid, int partition, String order);

	/**
	 * 获取简单的作品信息，仅包含作品id和标题
	 * @param partition
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<T> getSimpleWritingList(int partition, String order);

	/**
	 * 修改作品
	 * @param t
	 * @return
	 * @throws Exception
	 */
	ResultType updateWriting(T t);

	/**
	 * 删除作品
	 * @param writingId 作品编号
	 * @param deleterId 删除者账号
	 * @return
	 * @throws Exception
	 */
	ResultType deleteWriting(Long writingId, Long deleterId);

	/**
	 * 获取所有文章/问贴的id
	 * @return
	 * @throws Exception
	 */
	List<Long> getAllWritingsId();

	/**
	 * 通过id列表获取多个作品
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<EsBo> getWritingListByIds(List<Long> ids) ;
}
