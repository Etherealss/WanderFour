package service.impl;

import common.bean.SensitiveNode;
import common.factory.DaoFactory;
import common.util.JdbcUtil;
import dao.SensitiveDao;
import filter.SensitiveFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private SensitiveDao dao;
	private Logger logger = Logger.getLogger(SensitiveServiceImpl.class);

	@Override
	public void insertSensitiveWord(int type, String path) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String temp;
			while ((temp = reader.readLine()) != null) {
				dao.insertSensitiveDao(type, temp);
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

//	@Override
//	public Map<String, Object> getSensitiveWordsMap() {
//		//初始化敏感词树
//		Connection conn;
//		try {
//			conn = JdbcUtil.getConnection();
//			List<String> sensitiveWordsList = dao.getSensitiveWordsList(conn);
//			Map<String, Object> wordMap = SensitiveFilter.transWordListToHashMap(sensitiveWordsList);
//			return wordMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public Map<Character, SensitiveNode> getSensitiveWordsMap() {
		//初始化敏感词树
		List<String> sensitiveWordsList = dao.getSensitiveWordsList();
		Map<Character, SensitiveNode> wordMap =
				SensitiveFilter.transWordListToHashMap(sensitiveWordsList);
		return wordMap;
	}
}
