package service.impl;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import org.apache.log4j.Logger;
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
	public JSONObject getAllCategoryByPart(int partition) throws Exception{
		logger.trace("获取分区信息");
		Connection conn = JdbcUtil.getConnection();
			CategoryDao dao = DaoFactory.getCategoryDao();
			//获取分类信息包
			List<Map<String, Object>> maps = dao.selectAllCategoryByPart(conn, partition);
			//封装到json中
			JSONObject json = new JSONObject();
			for (Map<String, Object> map : maps) {
				json.put(String.valueOf(map.get("id")), map.get("name"));
			}

			return json;
	}
}
