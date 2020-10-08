import common.dto.ResultState;
import common.enums.Partition;
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
	public void resultStateTest(){
		System.out.println(ResultState.EXCEPTION);
		Map<String, Object> map = new HashMap<>();
		map.put("code1", ResultState.SUCCESS);
		map.put("code2", ResultState.EXCEPTION);
		System.out.println(map);
	}

	@Test
	public void partitionTest(){
		System.out.println(Partition.LEARNING);
		Map<String, Object> map = new HashMap<>();
		map.put("code1", Partition.LEARNING);
		map.put("code2", Partition.COLLEGE);
		System.out.println(map);
		System.out.println(Partition.getCode("大学风采"));
	}
}
