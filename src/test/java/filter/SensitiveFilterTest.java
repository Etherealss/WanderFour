package filter;

import common.structure.SensitiveNode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SensitiveFilterTest {
	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void testGetSensitiveWordsMap() throws Exception {
		Map<Character, SensitiveNode> sensitiveWordsMap = SensitiveFilter.getSensitiveWordsMap();
		logger.debug(sensitiveWordsMap.toString());
	}

	@Test
	public void testCheckChar() throws Exception {
		String s = "吃";
		Map<Character, SensitiveNode> sensitiveWordsMap = SensitiveFilter.getSensitiveWordsMap();
		//截取字符
		char c = s.charAt(0);
		//获取字符对应的Map
		SensitiveNode wordMap = sensitiveWordsMap.get(c);
		if (wordMap != null) {
			logger.debug(wordMap.toString());
		} else {
			logger.debug("null");
		}
	}


	@Test
	public void testCheckString() throws Exception {
		logger.debug("aaa");
		String txt = "法.轮.功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
				+ "仿真枪QQ卡复制器静的发呆着吃包二奶太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。\"\n" +
				"然后难过就躺在某一个人的怀里出售国产军用54手枪尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三.级.片 深人静的晚上，关上电话静静的发呆着。";
		SensitiveFilter filter = new SensitiveFilter();
		String s = filter.checkString(txt);
		logger.debug("aaa\n" + txt + "\n" + s);
	}

	@Test
	public void testReplaceAll() throws Exception {
		String txt = "操操操操卡复制器静的发呆着。";

		String s = txt.replaceAll("操", "*");
		logger.debug(s);
	}
}