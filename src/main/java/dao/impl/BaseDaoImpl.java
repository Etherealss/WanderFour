package dao.impl;

import common.annontation.Db;
import common.annontation.DbFieldId;
import common.annontation.DbTable;
import dao.BaseDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {
	protected Logger logger;
	protected QueryRunner qr = new QueryRunner();

	/** 子类的泛型类 */
	private final Class<T> CLAZZ;

	{
		//this指向继承BaseDAO的子类对象本身，获取该子类选择的泛型类型
		ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获取泛型参数，赋值给clazz
		Type[] typeArguments = paramType.getActualTypeArguments();
		CLAZZ = (Class<T>) typeArguments[0];
		logger = Logger.getLogger(CLAZZ);
	}

	/**
	 * 通过子类实现的泛型类获取PO对象的数据库名
	 * @return PO对象对应的数据库表所在的数据库名
	 */
	protected String getDbName(){
		Db db = CLAZZ.getAnnotation(Db.class);
		return db.DbName();
	}

	/**
	 * 通过子类实现的泛型类获取PO对象的表名
	 * @return PO对象对应的数据库表名
	 */
	protected String getTableName(){
		System.out.println(CLAZZ);
		DbTable table = CLAZZ.getAnnotation(DbTable.class);
		return table.tableName();
	}

	/**
	 * 获取PO对象的数据库名和表名
	 * @return 格式为 `数据库名.表名` 的字符串
	 */
	protected String getFullTableName(){
		return "`" + getDbName() + "." + getTableName() + "`";
	}

	/**
	 * 通过子类实现的泛型类获取PO对象的表名
	 * @return PO对象属性对应的数据库字段
	 */
	protected String getFliedId(){
		Field[] fs = CLAZZ.getDeclaredFields();
		for(Field field : fs) {
			if(field.isAnnotationPresent(DbFieldId.class)){
				return field.getName();
			}
		}
		return "";
	}
}
