package service;

import common.bean.SensitiveNode;

import java.util.Map;

/**
 * @author 寒洲
 * @description 敏感词
 * @date 2020/10/26
 */
public interface SensitiveService {

	/**
	 * 插入敏感词记录
	 * @param type
	 * @param path
	 */
	void insertSensitiveWord(int type, String path);

	/**
	 * 获取敏感词列表
	 * @return
	 */
	Map<Character, SensitiveNode> getSensitiveWordsMap();
}
