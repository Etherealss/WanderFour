package common.util;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SecurityUtilTest {

	private Logger logger = Logger.getLogger(SecurityUtilTest.class);


	@Test
	public void testEnsureHtmlSafe() throws Exception {
		String s = "傲娇地基爱打架萨拉大理石大佬的甲氨蝶呤卡机";
		logger.debug(StringEscapeUtils.escapeHtml(s));
	}

	@Test
	public void testEnsureJsSafe() throws Exception {
		String s = "<p>alertada啊啊啊(123)</p>";
		String s1 = StringEscapeUtils.escapeJavaScript(s);
		logger.debug(s1);
		logger.debug(unicodeToChinese(s1));
	}

	@Test
	public void testEnsureSQLSafe() throws Exception {
		String s = "or 1=1";
		String s1 = StringEscapeUtils.escapeSql(s);
		logger.debug(s);
		logger.debug(s1);
	}


	/**
	 * 【判断是否为中文字符】
	 * @param c
	 * @return 返回判断结果 - boolean类型
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 【Unicode转中文】
	 * @param unicode
	 * @return 返回转码后的字符串 - 中文格式
	 */
	public static String unicodeToChinese(final String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 0; i < hex.length; i++) {
			try {
				// 汉字范围 \u4e00-\u9fa5 (中文)
				if (hex[i].length() >= 4) {//取前四个，判断是否是汉字
					String chinese = hex[i].substring(0, 4);
					try {
						int chr = Integer.parseInt(chinese, 16);
						boolean isChinese = isChinese((char) chr);
						//转化成功，判断是否在  汉字范围内
						if (isChinese) {//在汉字范围内
							// 追加成string
							string.append((char) chr);
							//并且追加  后面的字符
							String behindString = hex[i].substring(4);
							string.append(behindString);
						} else {
							string.append(hex[i]);
						}
					} catch (NumberFormatException e1) {
						string.append(hex[i]);
					}
				} else {
					string.append(hex[i]);
				}
			} catch (NumberFormatException e) {
				string.append(hex[i]);
			}
		}
		return string.toString();
	}
}