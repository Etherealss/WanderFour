package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/25
 */
public interface SensitiveDao {

	/**
	 * 插入敏感词
	 * @param type
	 * @param word
	 * @throws SQLException
	 */
	void insertSensitiveDao(@Param("type") int type, @Param("word") String word);


	/**
	 * 获取敏感词
	 * @return
	 * @throws SQLException
	 */
	List<String> getSensitiveWordsList();
}
