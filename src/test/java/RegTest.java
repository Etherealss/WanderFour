import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.po.Article;
import pojo.po.Writing;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/10
 */
public class RegTest {

	private Logger logger = LoggerFactory.getLogger("testLogger");

	@Test
	public void regTest1() {

		URL adsad = getClass().getResource("adsad");


		Icon icon = new ImageIcon("D:/123.png");









		// 按指定模式在字符串查找
		String line = "";
		String pattern = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";

		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);

		// 现在创建 matcher 对象
		Matcher m = r.matcher(line);
		if (m.find()) {
			System.out.println("MATCH");
		} else {
			System.out.println("NOT MATCH");
		}
	}

	@Test
	public void dateTest() {

		System.out.println(new Date());
	}


	@Test
	public void testReg2() throws Exception {
		String targetTxt = "`";
		String pattern1 = "^[\u4e00-\u9fa5]+$";
		String pattern2 = "[a-zA-Z0-9]+";
		String pattern3 = "`";
		// 创建 Pattern 对象
		Pattern r1 = Pattern.compile(pattern1);
		Pattern r2 = Pattern.compile(pattern2);
		Pattern r3 = Pattern.compile(pattern3);
		// 现在创建 matcher 对象
		Matcher m1 = r1.matcher(targetTxt);
		Matcher m2 = r2.matcher(targetTxt);
		Matcher m3 = r2.matcher(targetTxt);
		if (m1.find()) {
			System.out.println("MATCH");
		} else {
			System.out.println("NOT MATCH");
		}
		if (m2.find()) {
			System.out.println("MATCH");
		} else {
			System.out.println("NOT MATCH");
		}
	}

	@Test
	public void testCastWriting() throws Exception {
		Writing writing = new Article();
		writing.setId(1L);
		writing.setContent("123123");
		Article article = (Article) writing;
		logger.debug(article.toString());
	}
}
