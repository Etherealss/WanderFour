package service.impl;

import common.dto.ResultState;
import common.util.JdbcUtil;
import dao.WritingDao;
import dao.impl.WritingDaoImpl;
import org.apache.log4j.Logger;
import pojo.Writing;
import service.WritingService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class WritingServiceImpl<T extends Writing> implements WritingService<T> {

	/** 子类的泛型类 */
	private Logger logger = Logger.getLogger(WritingServiceImpl.class);
	private WritingDao<T> dao = new WritingDaoImpl<>();

	{
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		logger.debug(genericSuperclass);
		ParameterizedType paramType = (ParameterizedType) genericSuperclass;
		//获取泛型参数，赋值给clazz
		//参数化类型中可能有多个泛型参数
		Type[] typeArguments = paramType.getActualTypeArguments();
		//获取数据的第一个泛型元素
		Class<T> CLAZZ = (Class<T>) typeArguments[0];
		logger.debug(CLAZZ);
		dao.setClazz(CLAZZ);
	}

	@Override
	public ResultState publishNewWriting(Writing writing) {
		logger.trace("发表新文章");
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			dao.updateNewArticle(conn, (T) writing);
			return ResultState.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultState.EXCEPTION;
		}
	}
}
