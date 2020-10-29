import org.junit.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/10
 */
public class RegTest {

	@Test
	public void regTest1() {
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


}
