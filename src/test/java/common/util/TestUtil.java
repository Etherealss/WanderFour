package common.util;

import common.enums.Partition;
import pojo.po.Article;
import pojo.po.Posts;

import java.util.Date;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/15
 */
public class TestUtil {

	public static Article getDefaultArticlePo(){
		Article writing = new Article();
		writing.setPartition("learning");
		writing.setAuthorId(1L);
		writing.setCategory(1);
		writing.setTitle("TestUtil测试");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		return writing;
	}

	public static Posts getDefaultPostsPo(){
		Posts writing = new Posts();
		writing.setPartition("learning");
		writing.setAuthorId(1L);
		writing.setCategory(1);
		writing.setTitle("TestUtil测试");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		return writing;
	}
}