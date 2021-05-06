package filter;

import common.structure.SensitiveNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.SensitiveService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/26
 */
public class SensitiveFilter {
	private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

	/**
	 * 敏感词树
	 */
	private static Map<Character, SensitiveNode> sensitiveWordMap;

	public static Map<Character, SensitiveNode> transWordListToHashMap(List<String> keyWordSet) {
		//初始化敏感词容器，减少扩容操作
		//迭代keyWordSet
		Map<Character, SensitiveNode> sensitiveWordMap = new HashMap<>(keyWordSet.size());
		Map<Character, SensitiveNode> nowMap = null;
		SensitiveNode sensitiveNode = null;
		for (String key : keyWordSet) {
//			if ("三.级.片".equals(key)){
//				System.out.println();
//			}
			//新的敏感词，获取整棵树，方便待会儿检查敏感词是否已存在结点
			//两个引用指向同个内存地址
			nowMap = sensitiveWordMap;
			//将敏感词字符串拆分成字符
			for (int i = 0; i < key.length(); i++) {
				//敏感词转换成char型
				Character c = key.charAt(i);
				//检查是否已存在结点
				SensitiveNode node = nowMap.get(c);
				if (node != null) {
					//已存在，获取该节点，所有树的大小，方便匹配到最小的树
					nowMap = node.getMap();

				} else {
					//不存在，新建结点
					sensitiveNode = new SensitiveNode();
					nowMap.put(c, sensitiveNode);
					//更改结点，接下来将在这个结点下继续添加结点，构成一个敏感词树
					nowMap = sensitiveNode.getMap();
				}
				if (key.length() - 1 == i) {
					// 获取了一个完整的敏感词，添加结束标识
					assert sensitiveNode != null;
					sensitiveNode.setEnd(true);
				}
			}
		}
		return sensitiveWordMap;
	}

	public static Map<Character, SensitiveNode> getSensitiveWordsMap() {
		logger.trace("获取敏感词数据");
		if (sensitiveWordMap == null) {
			logger.trace("初始化敏感词数据");

			ApplicationContext ac =
					new ClassPathXmlApplicationContext("spring/spring-config.xml");
			SensitiveService sensitiveService =
					(SensitiveService) ac.getBean("sensitiveService");
			//获取敏感词树
			Map<Character, SensitiveNode> sensitiveWordsMap =
					sensitiveService.getSensitiveWordsMap();
			//添加到内存
			setSensitiveWordMap(sensitiveWordsMap);
		}
		return sensitiveWordMap;
	}

	private static void setSensitiveWordMap(Map<Character, SensitiveNode> sensitiveWordMap) {
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
		Map<Character, SensitiveNode> sensitiveWordsMap = getSensitiveWordsMap();

		int len = txt.length();
		//遍历字符串
		for (int i = 0; i < len; i++) {
			//敏感词拼接
			StringBuilder fragment = new StringBuilder();
			/*
			匹配到的敏感词map，初始化为敏感词集合
			每次匹配到敏感词组成都会细化到下一分支
			 */
			Map<Character, SensitiveNode> wordMap = sensitiveWordsMap;
			//表示是否拼接完一个完整的敏感词
			boolean match = false;
			int front = i;
			//替换后字符串长度改变，要动态改变阈值
			for (int j = i; j < txt.length(); j++) {
				//截取字符
				Character c = txt.charAt(j);
				//获取字符对应的Map
				SensitiveNode sensitiveNode = wordMap.get(c);

				if (sensitiveNode != null) {
					//是敏感词的组成
					//拼接敏感词
					fragment.append(c);
					boolean isEnd = sensitiveNode.isEnd();
					if (isEnd) {
						match = true;
						//完整的敏感词长度
						front = j - i + 1;
					}
					wordMap = sensitiveNode.getMap();
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

}
