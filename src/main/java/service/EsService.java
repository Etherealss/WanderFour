package service;

import pojo.po.Article;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/10
 */
public interface EsService {

	/**
	 * 添加索引
	 * @throws IOException
	 */

	void createIndex() throws IOException;

	/**
	 * 根据索引名删除索引
	 *
	 * @param indexName 索引名
	 * @return
	 * @throws IOException
	 */
	boolean deleteIndex(String indexName) throws IOException;

	/**
	 * 判断索引是否存在
	 *
	 * @param indexName 索引名
	 * @return
	 * @throws IOException
	 */
	boolean existsIndex(String indexName) throws IOException;

	/**
	 * 新增文档
	 *
	 * @param jsonMap   索引字段key和value
	 * @param indexName 索引名名称
	 * @param rowId     文档id，指定生成的文档id，如果为空，es会自动生成id
	 * @throws IOException
	 * @return 如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
	 */
	String addDoc(Map<String, Object> jsonMap, String indexName, String rowId) throws IOException;

	/**
	 * 根据文档id，删除文档
	 *
	 * @param indexName 索引名
	 * @param id        文档id
	 * @throws IOException
	 * @return 如果返回结果deleted，删除成功，如果返回结果是not_found，文档不存在，删除失败
	 */
	String deleteDoc(String indexName, String id) throws IOException;
	/**
	 * 根据文档id，更新文档，如果返回结果为UPDATED，更新成功，否则更新失败
	 *
	 * @param jsonMap   待更新的文档信息
	 * @param indexName 索引名
	 * @param rowId     索引id
	 * @throws IOException
	 * @return
	 */
	String updateDoc(Map<String, Object> jsonMap, String indexName, String rowId) throws IOException;

	/**
	 * 批量操作
	 * @param indexName 索引名称
	 * @param docs      文档列表
	 * @param action    增删改操作
	 * @return 如果返回结果为SUCCESS，则全部记录操作成功，否则至少一条记录操作失败，并返回失败的日志
	 * @throws IOException
	 */
	String bulkDoc(String indexName, List<Article> docs, String action) throws IOException;
}
