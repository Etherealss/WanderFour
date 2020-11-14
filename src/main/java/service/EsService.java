package service;

import pojo.po.Article;
import pojo.po.Writing;

import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description ES搜索
 * @date 2020/11/10
 */
public interface EsService {

	/**
	 * 添加索引
	 * @return
	 */
	boolean createWritingIndex();

	/**
	 * 根据索引名删除索引
	 * @param indexName 索引名
	 * @return
	 */
	boolean deleteIndex(String indexName);

	/**
	 * 判断索引是否存在
	 * @param indexName 索引名
	 * @return
	 */
	boolean existsIndex(String indexName);

	/**
	 * 新增文档
	 * @param writing   添加的文档
	 * @param indexName 索引名名称
	 * @param rowId     文档id，指定生成的文档id，如果为空，es会自动生成id
	 * @return 如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
	 */
	String addDoc(Writing writing, String indexName, String rowId);

	/**
	 * 根据文档id，删除文档
	 * @param indexName 索引名
	 * @param id        文档id
	 * @return 如果返回结果deleted，删除成功，如果返回结果是not_found，文档不存在，删除失败
	 */
	String deleteDoc(String indexName, String id);

	/**
	 * 根据文档id，更新文档，如果返回结果为UPDATED，更新成功，否则更新失败
	 * @param jsonMap   待更新的文档信息
	 * @param indexName 索引名
	 * @param rowId     索引id
	 * @return
	 */
	String updateDoc(Map<String, Object> jsonMap, String indexName, String rowId);

	/**
	 * 批量操作
	 * @param indexName 索引名称
	 * @param docs      文档列表
	 * @param action    增删改操作
	 * @return 如果返回结果为SUCCESS，则全部记录操作成功，否则至少一条记录操作失败，并返回失败的日志
	 */
	String bulkDoc(String indexName, List<Article> docs, String action);

	/**
	 * 高亮、多字段 搜索
	 * @param word
	 * @param from
	 * @param size
	 */
	void searchByHighLigh(String word, int from, int size);
}
