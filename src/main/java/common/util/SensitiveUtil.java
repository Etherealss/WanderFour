package common.util;

import common.factory.ServiceFactory;
import org.apache.log4j.Logger;
import service.SensitiveService;

import java.util.*;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/26
 */
public class SensitiveUtil {
	private static Logger logger = Logger.getLogger(SensitiveUtil.class);

	/**
	 * 敏感词树
	 */
	private static Map<String, Object> sensitiveWordMap;

	public static Map<String, Object> addSensitiveWordToHashMap(List<String> keyWordSet) {
		//初始化敏感词容器，减少扩容操作
		/*
		本来可以使用Map<Character, Object>的，
		但是因为需要一个标识isEnd来判断
		如果使用character，容易误判
		因为单个字符也可能是敏感词的组成
		所以使用字符串作为结束表示肯定不会出错
		String会在字符串的末尾添加\0，所以会占用更多空间
		TODO 待优化
		*/
		Map<String, Object> sensitiveWordMap = new HashMap(keyWordSet.size());
		Map<String, Object> nowMap = null;
		Map<String, Object> newWordMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while (iterator.hasNext()) {
			//关键字
			String key = iterator.next();
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				//敏感词转换成char型
				char keyChar = key.charAt(i);
				//获取
				Object wordMap = nowMap.get(keyChar);
				logger.debug("keyChar = " + keyChar + ", wordMap = " + wordMap);
				if (wordMap != null) {
					//如果存在该key，直接赋值
					nowMap = (Map<String, Object>) wordMap;
				} else {
					//不存在则，则构建一个map，同时将end设置为0，表示该敏感词不完整
					newWordMap = new HashMap<>(5);

					//不是最后一个
					newWordMap.put("isEnd", "0");
					nowMap.put(String.valueOf(keyChar), newWordMap);
					nowMap = newWordMap;
				}

				if (i == key.length() - 1) {
					//获取了一个完整的敏感词，添加结束标识
					nowMap.put("isEnd", "1");
				}
			}
		}
		return sensitiveWordMap;
	}

	public static Map<String, Object> getSensitiveWordMap() {
		return sensitiveWordMap;
	}

	public static void setSensitiveWordMap(Map<String, Object> sensitiveWordMap) {
		SensitiveUtil.sensitiveWordMap = sensitiveWordMap;
	}

	public static String checkString(String txt) {
		Map<String, Object> sensitiveWordsMap =
				ServiceFactory.getSensitiveService().getSensitiveWordsMap();
		//前后指针
		int front = 0, rear = 1;
		int len = txt.length();
		StringBuilder fragment = new StringBuilder();
		for (int i = 0; i < len; i++) {
			//尺取法
			for (int j = i; j < len; j++) {
				char c = txt.charAt(j);
				Map<String, Object> wordMap = (Map<String, Object>) sensitiveWordsMap.get(c);
				if (wordMap != null) {
					//是敏感词的组成
					rear++;
				} else {

					break;
				}
			}


		}
		return txt;
	}

//	/**
//	 * 判断是否存在敏感词
//	 * @param str
//	 * @param sensitiveWordsMap
//	 * @return
//	 */
//	private static boolean containSensitive(String str, Map<String, Object> sensitiveWordsMap) {
//		int count = 0;
//		for (int i = 0; i < str.length(); i++) {
//			char c = str.charAt(i);
//			Map<String, Object> wordMap = (Map<String, Object>) sensitiveWordsMap.get(c);
//			if (wordMap != null) {
//				//该字是敏感词（的一部分）
//				count++;
//				if ("1".equals(wordMap.get("isEnd"))) {
//				}
//			} else {
//
//			}
//		}
//		return false;
//	}
}
