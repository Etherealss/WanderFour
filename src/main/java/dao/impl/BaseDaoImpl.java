package dao.impl;

import dao.BaseDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	protected Logger logger = Logger.getLogger(BaseDaoImpl.class);
	protected QueryRunner qr = new QueryRunner();
}
