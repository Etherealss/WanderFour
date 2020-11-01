package common.strategy;

import pojo.po.Writing;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/31
 */
public interface SearchWritingStrategy {

	/**
	 * 获取列表
	 * @param mainWord
	 * @return
	 */
	List<Writing> getWriting(String mainWord);
}
