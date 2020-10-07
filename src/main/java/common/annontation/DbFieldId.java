package common.annontation;

import java.lang.annotation.*;

/**
 * @author 寒洲
 * @description 标识主键
 * @date 2020/10/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbFieldId   {
}
