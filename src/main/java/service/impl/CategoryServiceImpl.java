package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import dao.CategoryDao;
import org.apache.log4j.Logger;
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

	@Override
	public JSONObject getAllCategoryByPart(int partition) throws Exception {
		Jedis jedis = JedisUtil.getJedis();
		String category = jedis.get("Category");
		JSONObject returnJson;
		if (category != null) {
			//缓存中存在分类数据
			logger.trace("从 缓存 中获取分区信息");
			returnJson = JSONObject.parseObject(category);
		} else {
			//缓存中没有category分类数据
			logger.trace("从 数据库 中获取分区信息");
			Connection conn = JdbcUtil.getConnection();
			CategoryDao dao = DaoFactory.getCategoryDao();
			//获取分类信息包
			List<Map<String, Object>> maps = dao.getAllCategoryByPart(conn, partition);
			//封装到json中
			returnJson = new JSONObject();
			for (Map<String, Object> map : maps) {
				returnJson.put(String.valueOf(map.get("id")), map.get("name"));
			}
			jedis.set("Category", returnJson.toJSONString());
		}
		return returnJson;
	}
}
