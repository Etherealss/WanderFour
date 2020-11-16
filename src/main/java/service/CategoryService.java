package service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 寒洲
 * @description 分类Service
 * @date 2020/10/19
 */
public interface CategoryService {

	/**
	 * 通过分区获取所有可选分类
	 * @param partition
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllCategoryByPart(int partition) throws Exception;

	/**
	 * 获取所有分区的可选分类
	 * @return
	 * @throws Exception
	 */
	JSONObject getAllCategory() throws Exception;
}
