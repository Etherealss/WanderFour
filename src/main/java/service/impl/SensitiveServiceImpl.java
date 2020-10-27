package service.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import common.util.JedisUtil;
import common.util.SensitiveUtil;
import dao.SensitiveDao;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import service.SensitiveService;

import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description 敏感词
 * @date 2020/10/26
 */
public class SensitiveServiceImpl implements SensitiveService {

	private SensitiveDao dao = DaoFactory.getSensitiveDao();
	private Logger logger = Logger.getLogger(SensitiveServiceImpl.class);

	@Override
	public void insertSensitiveWord() {
		BufferedReader reader = null;
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			String path = "C:\\Users\\寒洲\\Desktop\\sensitive-words-master\\涉枪涉爆违法信息关键词.txt";
			reader = new BufferedReader(new FileReader(path));
			String temp;
			while ((temp = reader.readLine()) != null) {
				System.out.println(temp);
				dao.insertSensitiveDao(conn, 2, temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Map<String, Object> getSensitiveWordsMap() {
		Map<String, Object> sensitiveWordMap = SensitiveUtil.getSensitiveWordMap();
		if (sensitiveWordMap != null) {
			//如果内存中已存在敏感词树，则获取并返回
			return sensitiveWordMap;
		} else {
			//初始化敏感词树
			Connection conn;
			try {
				conn = JdbcUtil.getConnection();
				List<String> sensitiveWordsList = dao.getSensitiveWordsList(conn);
				Map<String, Object> wordMap = SensitiveUtil.addSensitiveWordToHashMap(sensitiveWordsList);
				//添加到内存
				SensitiveUtil.setSensitiveWordMap(wordMap);
				return wordMap;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
