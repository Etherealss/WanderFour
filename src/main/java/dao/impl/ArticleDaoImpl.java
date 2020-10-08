package dao.impl;

import common.annontation.DbTable;
import dao.ArticleDao;
import org.apache.commons.dbutils.QueryRunner;
import pojo.Writing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 寒洲
 * @description 文章DAO
 * @date 2020/10/5
 */
public class ArticleDaoImpl<T extends Writing> implements ArticleDao<T> {
	protected QueryRunner qr = new QueryRunner();
	/**
	 * 通过子类实现的泛型类获取PO对象的表名
	 * @return PO对象对应的数据库表名
	 */
	private String getTableName(){
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		System.out.println(genericSuperclass);
		ParameterizedType paramType = (ParameterizedType) genericSuperclass;
		//获取泛型参数，赋值给clazz
		Type[] typeArguments = paramType.getActualTypeArguments();
		Class<T> CLAZZ = (Class<T>) typeArguments[0];
		System.out.println(CLAZZ);
		DbTable table = CLAZZ.getAnnotation(DbTable.class);
		return table.tableName();
	}

	@Override
	public void updateNewArticle(Connection conn, Writing a) throws SQLException {
		String sql = "insert into " + getTableName() +
				"(category, author_id, title," +
				"label1, label2, label3, label4, label5, " +
				"create_time, update_time, liked, collected)" +
				"values(?,?,?, ?,?,?,?,?, ?,?,?,?)";
		Object[] params = {a.getCategory(), a.getAuthorId(), a.getTitle(),
				a.getLabel1(),a.getLabel2(),a.getLabel3(),a.getLabel4(),a.getLabel5(),
				a.getCreateTime(),a.getUpdateTime(),a.getLiked(),a.getCollected()};
		int res = qr.update(conn, sql, params);
		assert res == 1;
	}

	@Override
	public T selectArticleById(Connection conn, Long id) throws SQLException {
		return null;
	}

	@Override
	public Long countArticle(Connection conn, int partition) throws SQLException {
		return null;
	}

	@Override
	public List<T> getBlogListByPage(Connection conn, int partition, int start, int rows) throws SQLException{
		return null;
	}

	@Override
	public int getUserArticleCount(Connection conn, String userid) throws SQLException{
		return 0;
	}
}
