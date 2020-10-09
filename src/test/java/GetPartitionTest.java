import common.enums.Partition;
import org.junit.Test;
import pojo.po.CollegeArticle;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class GetPartitionTest {

	@Test
	public void getPartitionTest(){
		Partition partition = CollegeArticle.PARTITION;
		System.out.println(partition);
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
