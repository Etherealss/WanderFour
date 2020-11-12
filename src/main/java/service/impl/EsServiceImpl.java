package service.impl;


import common.util.EsUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import pojo.po.Article;
import service.EsService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/10
 */
public class EsServiceImpl implements EsService {

	Logger logger = Logger.getLogger(EsServiceImpl.class);
	private static final String INDEX = "writings";
	private RestHighLevelClient client = EsUtil.getClient();


	@Override
	public void createIndex() throws IOException {
		//准备索引相关的setting
		Settings.Builder settings = Settings.builder()
				.put("analysis.analyzer.default.type", "ik_max_word")
				.put("number_of_shards", 5)
				.put("number_of_replicas", 1);

		// 准备关于索引结构的mappings
		XContentBuilder mappings = JsonXContent.contentBuilder()
				.startObject()
				.startObject("properties")
				.startObject("title")
				.field("type", "text")
				.endObject()
				.startObject("content")
				.field("type", "text")
				.endObject()
				.startObject("category")
				.field("type", "keyword")
				.endObject()
				.startObject("label1")
				.field("type", "keyword")
				.endObject()
				.startObject("label2")
				.field("type", "keyword")
				.endObject()
				.startObject("label3")
				.field("type", "keyword")
				.endObject()
				.startObject("label4")
				.field("type", "keyword")
				.endObject()
				.startObject("label5")
				.field("type", "keyword")
				.endObject()
				.endObject()
				.endObject();

		// 将settings和mappings封装到Request中
		CreateIndexRequest request = new CreateIndexRequest(INDEX)
				.settings(settings)
				.mapping(mappings);
		// 通过client对象连接ES并执行
		CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
	}

	@Override
	public boolean deleteIndex(String indexName) throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		AcknowledgedResponse respone = client.indices().delete(request, RequestOptions.DEFAULT);
		return respone.isAcknowledged();
	}

	@Override
	public boolean existsIndex(String indexName) throws IOException {
		GetIndexRequest request = new GetIndexRequest(indexName);
		return client.indices().exists(request, RequestOptions.DEFAULT);
	}

	@Override
	public String addDoc(Map<String, Object> jsonMap, String indexName, String rowId) throws IOException {
		IndexRequest indexRequest = new IndexRequest(indexName)
				.id(rowId)
				.source(jsonMap);
		IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
		//如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
		DocWriteResponse.Result result = response.getResult();
		return result.toString();
	}

	@Override
	public String deleteDoc(String indexName, String id) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		DocWriteResponse.Result result = response.getResult();
		return result.toString();
	}


	@Override
	public String updateDoc(Map<String, Object> jsonMap, String indexName, String rowId) throws IOException {
		UpdateRequest request = new UpdateRequest(indexName, rowId).doc(jsonMap);
		UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		DocWriteResponse.Result result = response.getResult();
		logger.debug("updateDoc: " + result.toString());
		return result.toString();
	}


	@Override
	public String bulkDoc(String indexName, List<Article> docs, String action) throws IOException {
		BulkRequest request = new BulkRequest();
		switch (action) {
			case "add":
				for (Article user : docs) {
					request.add(new IndexRequest(indexName).id(user.getId().toString())
							.source(XContentType.JSON, "title", user.getTitle(),
									"content", user.getContent(),
									"category", user.getCategory(),
									"label1", user.getLabel1(),
									"label2", user.getLabel2(),
									"label3", user.getLabel3(),
									"label4", user.getLabel4(),
									"label5", user.getLabel5()
							)
					);
				}
				break;
			case "update":
				for (Article user : docs) {
					request.add(new UpdateRequest(indexName, user.getId().toString())
							.doc(XContentType.JSON, "title", user.getTitle(),
									"content", user.getContent(),
									"category", user.getCategory(),
									"label1", user.getLabel1(),
									"label2", user.getLabel2(),
									"label3", user.getLabel3(),
									"label4", user.getLabel4(),
									"label5", user.getLabel5()
							)
					);
				}
				break;
			case "delete":
			default:
				for (Article user : docs) {
					request.add(new DeleteRequest(indexName, user.getId().toString()));
				}
		}
		BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

		// 获取结果日志
		return getBulkRespLog(bulkResponse);
	}

	/**
	 * 获取ES操作结果日志
	 * @param bulkResponse
	 * @return 结果日志，如果操作全部成功，返回"SUCCESS"
	 */
	private String getBulkRespLog(BulkResponse bulkResponse) {
		// 如果至少有一个操作失败，此方法返回true
		if (bulkResponse.hasFailures()) {
			StringBuilder sb = new StringBuilder();
			for (BulkItemResponse bulkItemResponse : bulkResponse) {
				//指示给定操作是否失败
				if (bulkItemResponse.isFailed()) {
					//检索失败操作的失败
					BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
					sb.append(failure.toString()).append("\n");
				}
			}
			logger.error("=bulk error=" + sb.toString());
			return sb.toString();
		} else {
			return "SUCCESS";
		}
	}




}
