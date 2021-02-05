package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/4
 */
public class ObjectUtil {

	/**
	 * 强制对象类型时添加检查代码
	 * @param returnClass 返回的类型
	 * @param value 想强转的对象
	 * @param <T> 返回的类型
	 * @return 强转为目标类型的对象
	 */
	public static <T> T objParse(Class<T> returnClass, Object value) {
		try {
			if (value.getClass().equals(returnClass)) {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.convertValue(value, returnClass);
			}
		} catch (Exception ignored) { //忽略
		}
		return null;
	}

	/**
	 * 强制对象类型时添加检查代码
	 * @param returnClass 返回的类型
	 * @param value 想强转的对象
	 * @param <T> 返回的类型
	 * @return 强转为目标类型的对象
	 */
	public static <T> T objParse(T returnClass, Object value) {
		try {
			if (value.getClass().equals(returnClass)) {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.convertValue(value,
						(Class<T>)((ParameterizedType)returnClass.getClass().
								getGenericSuperclass()).getActualTypeArguments()[0]);
			}
		} catch (Exception ignored) { //忽略
		}
		return null;
	}

	/**
	 * 调用obj对象的setter方法
	 * @param obj   操作的对象
	 * @param fieldName   操作的属性
	 * @param value 设置的值
	 */
	public static void invokeSetter(Object obj, String fieldName, Object value) {
		try {
			Class<?> clazz = obj.getClass();
			// 获取属性的数据类型
			Field field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);
			field.set(obj, value);
//			logger.debug("methodName = " + "set" + capiFirstLetter(fieldName)+ ", type = " + field.getType());
//			Method method = clazz.getMethod("set" + capiFirstLetter(fieldName), field.getType());
//			// 执行
//			method.invoke(obj, value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将单词的首字母大写
	 * @param str
	 * @return
	 */
	public static String capiFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
