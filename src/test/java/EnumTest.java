import common.dto.ResultState;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/3
 */
public class EnumTest {

	@Test
	public void enumTest(){
		System.out.println(ResultState.EXCEPTION);
		Map<String, Object> map = new HashMap<>();
		map.put("code1", ResultState.SUCCESS);
		map.put("code2", ResultState.EXCEPTION);
		System.out.println(map);
	}
}
