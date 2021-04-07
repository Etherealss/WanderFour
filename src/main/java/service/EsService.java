package service;

import com.alibaba.fastjson.JSONObject;
import common.enums.WritingType;
import pojo.bo.EsBo;
import pojo.bo.PageBo;

import java.util.List;

/**
 * @author 寒洲
 * @description ES搜索
 * @date 2020/11/10
 */
public interface EsService {

	/**
	 * 添加索引
	 * @return 已存在索引 或者 出现异常，返回false
	 */
	boolean createWritingIndex();

	/**
	 * 遍历作品id，检查是不是所有的作品都在ES中，并返回存在的作品的id
	 * @param type 文章"article" 问贴"posts"
	 * @param writingsId
	 * @return
	 */
	List<Long> checkWritingsExist(WritingType type, List<Long> writingsId);

	/**
	 * 初始化ES的数据
	 * @param type
	 * @param writingsId
	 * @param allCategory 获取所有的分类Json
	 * @throws Exception
	 */
	void initWritingDocs(WritingType type, List<Long> writingsId, JSONObject allCategory) throws Exception;

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
	 * @param indexName    索引名名称
	 * @param writing      添加的文档
	 * @return 如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
	 */
	String addDoc(String indexName, EsBo writing);

	/**
	 * 根据文档id，删除文档
	 * @param indexName 索引名
	 * @param id        文档id
	 * @return 如果返回结果deleted，删除成功，如果返回结果是not_found，文档不存在，删除失败
	 */
	String deleteDoc(String indexName, String id);

	/**
	 * 根据文档id，更新文档，如果返回结果为UPDATED，更新成功，否则更新失败
	 * @param indexName 索引名
	 * @param writing   待更新的文档信息
	 * @return
	 */
	String updateDoc(String indexName, EsBo writing);

	/**
	 * 批量操作
	 * @param indexName 索引名称
	 * @param action    增删改操作
	 * @param docs      文档列表
	 * @return 如果返回结果为SUCCESS，则全部记录操作成功，否则至少一条记录操作失败，并返回失败的日志
	 */
	String bulkDoc(String indexName, String action, List<EsBo> docs);

	/**
	 * 高亮、多字段 搜索
	 * @param word
	 * @param from
	 * @param size
	 * @return
	 */
	PageBo<EsBo> searchByHighLigh(String word, int from, int size);

	/**
	 * 前缀搜素
	 * @param word
	 * @param from
	 * @param size
	 * @return
	 */
	List<EsBo> searchByPrefix(String word, int from, int size);

	/**
	 * 搜索建议
	 * @param word
	 * @param indexName
	 * @param size
	 * @return
	 */
	List<String> querySuggestion(String word, String indexName, int size);
}
