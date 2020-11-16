package service.impl;


import common.util.EsUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import pojo.po.Article;
import pojo.po.Writing;
import service.EsOperator;
import service.EsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/10
 */
public class EsServiceImpl implements EsService {

	Logger logger = Logger.getLogger(EsServiceImpl.class);

	private RestHighLevelClient client = EsUtil.getClient();

	public static final String INDEX_NAME = "writings";
	/** ik分词器 */
	private static final String ANALYZER_IK_MAX_WORD = "ik_max_word";
	/** 最大分片数 */
	private static final int NUMBER_OF_SHARDS = 5;
	/** 数据备份数 */
	private static final int NUMBER_OF_REPLICAS = 1;

	@Override
	public boolean createWritingIndex() {
		RestHighLevelClient client = null;
		/*
		准备索引相关的setting
		确定分词器、最大分片数、数据备份数
		 */
		Settings.Builder settings = Settings.builder()
				.put("analysis.analyzer.default.type", ANALYZER_IK_MAX_WORD)
				.put("number_of_shards", NUMBER_OF_SHARDS)
				.put("number_of_replicas", NUMBER_OF_REPLICAS);

		// 准备关于索引结构的mappings
		XContentBuilder mappings = null;
		try {
			mappings = JsonXContent.contentBuilder()
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
			EsOperator operator = new EsOperator();
			client = EsUtil.getClient();
			CreateIndexResponse resp = operator.createIndex(client, settings, mappings, INDEX_NAME);
			return resp.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteIndex(String indexName) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();
			EsOperator operator = new EsOperator();
			AcknowledgedResponse resp = operator.deleteIndex(client, indexName);
			return resp.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean existsIndex(String indexName) {
		RestHighLevelClient client = null;
		try {
			EsOperator operator = new EsOperator();
			client = EsUtil.getClient();
			return operator.existsIndex(client, indexName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public String addDoc(Writing writing, String indexName, String docId) {
		Map<String, Object> jsonMap = new HashMap<>();
		if(writing instanceof Article){
			Article article = (Article) writing;
			jsonMap.put("title", article.getTitle());
			jsonMap.put("content", article.getContent());
		}

		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			IndexResponse resp = operator.addDoc(client, jsonMap, indexName, docId);

			//如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
			DocWriteResponse.Result result = resp.getResult();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String deleteDoc(String indexName, String docId) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();
			EsOperator operator = new EsOperator();
			DeleteResponse response = operator.deleteDoc(client, indexName, docId);
			DocWriteResponse.Result result = response.getResult();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String updateDoc(Map<String, Object> jsonMap, String indexName, String docId) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			UpdateResponse resp = operator.updateDoc(client, jsonMap, indexName, docId);

			DocWriteResponse.Result result = resp.getResult();

			logger.debug("updateDoc: " + result.toString());
			return result.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String bulkDoc(String indexName, List<Article> docs, String action) {
		BulkRequest request = new BulkRequest();
		// 封装request
		switch (action) {
			case "add":
				// 添加文档
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
				// 更新文档
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
				// 删除文档
			default:
				for (Article user : docs) {
					request.add(new DeleteRequest(indexName, user.getId().toString()));
				}
		}

		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			// 获取结果日志
			return getBulkRespLog(bulkResponse);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
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

	@Override
	public void searchByHighLigh(String word, int from, int size) {
		String[] fieldNames = {"title", "content", "label1", "label2", "label3", "label4", "label5"};
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			SearchResponse resp =
					operator.mulitHighLightQuery(client, INDEX_NAME, fieldNames, word, from, size);



		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


}
