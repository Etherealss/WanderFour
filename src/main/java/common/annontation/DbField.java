package common.annontation;

import java.lang.annotation.*;

/**
 * @author 寒洲
 * @description 标识字段
 * @date 2020/10/4
 */
@Target(ElementType.FIELD)  //字段上
@Retention(RetentionPolicy.RUNTIME)  //该注解被保存在class文件中,且可以被反射机制获取
@Inherited  //继承
@Documented  //javadoc保留注解信息
public @interface DbField {
	String value() ;
	boolean exist() default true;
}
