package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.Partition;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import common.util.JsonUtil;
import dao.CategoryDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import service.CategoryService;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/19
 */
public class CategoryServiceImpl implements CategoryService {

	private Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryDao dao;

	@Override
	public JSONObject getAllCategoryByPart(int partition) throws Exception {
		Jedis jedis = JedisUtil.getJedis();
		String category = jedis.get(getJedisCategoryIndex(partition));
		JSONObject returnJson;
		if (category != null) {
			//缓存中存在分类数据
			logger.trace("从 缓存 中获取分区信息");
			returnJson = JSONObject.parseObject(category);
		} else {
			//缓存中没有category分类数据
			logger.trace("从 数据库 中获取分区信息");
			dao = DaoFactory.getCategoryDao();
			//获取分类信息包
			List<Map<String, Object>> maps = dao.getAllCategoryByPart(partition);
			//封装到json中
			returnJson = new JSONObject();
			for (Map<String, Object> map : maps) {
				returnJson.put(String.valueOf(map.get("id")), map.get("name"));
			}
			jedis.set(getJedisCategoryIndex(partition), returnJson.toJSONString());
		}
		return returnJson;
	}

	@Override
	public JSONObject getAllCategory() throws Exception {
		JSONObject result = new JSONObject();
		// 获取所有分区
		Partition[] partitions = Partition.getAllPartition();
		// 遍历分区，获取所有分区的分类
		for (Partition p : partitions){
			int code = p.code();
			JSONObject allCategoryByPart = this.getAllCategoryByPart(code);
			// 拼接两个json
			JsonUtil.combineJson(result, allCategoryByPart);
		}
		return result;
	}

	/**
	 *
	 * @param partition
	 * @return "Category::" + partition
	 */
	private String getJedisCategoryIndex(int partition) {
		return "Category::" + partition;
	}
}
