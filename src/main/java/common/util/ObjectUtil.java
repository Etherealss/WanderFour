package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

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
}
