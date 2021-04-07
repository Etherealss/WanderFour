import common.enums.Partition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class GetPartitionTest {

	@Test
	public void testInstanceof(){
		Writing w = new Article();
		if (w instanceof Article){
			System.out.println("Article");
		}
		if (w instanceof Writing){
			System.out.println("Writing");
		}
		if (w instanceof Posts){
			System.out.println("Posts");
		}
	}
//	@Test
//	public void extendTest(){
//		A a = new B();
//		a.methodA();
//		Object o = (Object) a;
//		//这里需要泛型的话，会丧失泛型
//		B b2 = (B) o;
//		b2.methodb();
//	}
}

//class A{
//	protected String a = "this is A's string";
//	protected void methodA(){
//		System.out.println(a);
//	}
//}
//
//class B extends A{
//	protected String b = "this is B's String.";
//	protected void methodb(){
//		System.out.println(b);
//	}
//}
