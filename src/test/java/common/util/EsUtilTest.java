package common.util;

import common.enums.WritingType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.bo.EsBo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsUtilTest {
	private Logger logger = LoggerFactory.getLogger("testLogger");


	@Test
	public void testGetEsPath() throws Exception {
	}

	@Test
	public void testRunEsService() throws Exception {
	}

	@Test
	public void testGetClient() throws Exception {
	}

	@Test
	public void testIsEsHostConnected() throws Exception {
	}

	@Test
	public void testGetEsWritingMappings() throws Exception {
	}

	@Test
	public void testGetJsonObject1() throws Exception {
	}

	@Test
	public void testGetJsonObject2() throws Exception {
		Map<String, Object> source = new HashMap<>();
		source.put("writingId", 1L);
		source.put("writingType", WritingType.ARTICLE.val());
		EsBo json = EsUtil.getEsBoByMap(source);
		logger.debug("{}",json);
	}

	@Test
	public void testInvokeSetter() throws Exception {
		Class<?> esBoClass = Class.forName("pojo.bo.EsBo");
		EsBo esBo = (EsBo) esBoClass.newInstance();

		Map<String, Object> source = new HashMap<>();
		source.put("writingId", 1L);
		source.put("writingType", WritingType.ARTICLE.val());
		source.put("createTime", new Date());
		for (Map.Entry<String, Object> en : source.entrySet()) {
			ObjectUtil.invokeSetter(esBo, en.getKey(), en.getValue());
		}

		logger.debug("{}",esBo);
	}

	@Test
	public void testInvoke() throws Exception {
		try {
			EsBo esBo = new EsBo();
			Class<?> clazz = esBo.getClass();
			// 获取属性的数据类型
			Field field = clazz.getDeclaredField("writingId");

			Method method = clazz.getMethod("setWritingId", field.getType());
			// 执行
			method.invoke(esBo, 1L);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCapitalizeFirstLetter() throws Exception {
	}

	@Test
	public void testGetWritingTypeStr() throws Exception {
	}

	@Test
	public void testGetBulkRespLog() throws Exception {
	}
}