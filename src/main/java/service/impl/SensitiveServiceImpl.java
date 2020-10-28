package service.impl;

import common.factory.DaoFactory;
import common.util.JdbcUtil;
import filter.SensitiveFilter;
import dao.SensitiveDao;
import org.apache.log4j.Logger;
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
	public void insertSensitiveWord(int type, String path) {
		BufferedReader reader = null;
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			reader = new BufferedReader(new FileReader(path));
			String temp;
			while ((temp = reader.readLine()) != null) {
				dao.insertSensitiveDao(conn, type, temp);
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
		//初始化敏感词树
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			List<String> sensitiveWordsList = dao.getSensitiveWordsList(conn);
			Map<String, Object> wordMap = SensitiveFilter.transWordListToHashMap(sensitiveWordsList);
			return wordMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
