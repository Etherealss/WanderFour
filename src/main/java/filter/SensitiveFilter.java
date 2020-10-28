package filter;

import common.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/26
 */
public class SensitiveFilter {
	private static Logger logger = Logger.getLogger(SensitiveFilter.class);

	/**
	 * 敏感词树
	 */
	private static Map<String, Object> sensitiveWordMap;

	private static final String IS_END = "1";

	public static Map<String, Object> transWordListToHashMap(List<String> keyWordSet) {
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
		for (String key : keyWordSet) {
			//关键字
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				//敏感词转换成char型
				String keyChar = String.valueOf(key.charAt(i));
				//获取
				Object wordMap = nowMap.get(keyChar);
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

	public static Map<String, Object> getSensitiveWordsMap() {
		logger.trace("获取敏感词数据");
		if (sensitiveWordMap == null) {
			logger.trace("初始化敏感词数据");
			//获取敏感词树
			Map<String, Object> sensitiveWordsMap =
					ServiceFactory.getSensitiveService().getSensitiveWordsMap();
			//添加到内存
			setSensitiveWordMap(sensitiveWordsMap);
		}
		return sensitiveWordMap;
	}

	private static void setSensitiveWordMap(Map<String, Object> sensitiveWordMap) {
		SensitiveFilter.sensitiveWordMap = sensitiveWordMap;
	}

	/**
	 * 过滤敏感词
	 * @param txt
	 * @return
	 */
	public String checkString(String txt) {
		if (txt == null) {
			return null;
		}
		//获取内存中的敏感词树，如果没有会初始化
		Map<String, Object> sensitiveWordsMap = getSensitiveWordsMap();

		int len = txt.length();
		//遍历字符串
		for (int i = 0; i < len; i++) {
			//敏感词拼接
			StringBuilder fragment = new StringBuilder();
			/*
			匹配到的敏感词map，初始化为敏感词集合
			每次匹配到敏感词组成都会细化到下一分支
			 */
			Map<String, Object> wordMap = sensitiveWordsMap;
			//表示是否拼接完一个完整的敏感词
			boolean match = false;
			int front = i;
			//替换后字符串长度改变，要动态改变阈值
			for (int j = i; j < txt.length(); j++) {
				//截取字符
				String c = String.valueOf(txt.charAt(j));
				//获取字符对应的Map
				wordMap = (Map<String, Object>) wordMap.get(c);
				if (wordMap != null) {
					//是敏感词的组成
					//拼接敏感词
					fragment.append(c);
					String isEnd = wordMap.get("isEnd").toString();
					if (isEnd.equals(IS_END)) {
						match = true;
						//完整的敏感词长度
						front = j - i + 1;
					}
				} else {
					if (match) {
						String substring = fragment.substring(0, front);
						txt = txt.replaceAll(substring, "**");
						//同步指针，敏感词替换为两个长度的**，所以i加2
						i += 2;
					}
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
