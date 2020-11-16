package service.impl;


import com.alibaba.fastjson.JSONObject;
import common.enums.EsEnum;
import common.enums.ResultType;
import common.enums.WritingType;
import common.factory.DaoFactory;
import common.factory.ServiceFactory;
import common.util.EsUtil;
import common.util.JdbcUtil;
import dao.WritingDao;
import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
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
import pojo.bo.EsBo;
import pojo.po.Article;
import pojo.po.Writing;
import service.CategoryService;
import common.others.EsOperator;
import service.EsService;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/10
 */
public class EsServiceImpl implements EsService {

	Logger logger = Logger.getLogger(EsServiceImpl.class);

	private RestHighLevelClient client = EsUtil.getClient();

	/** ik分词器 */
	private static final String ANALYZER_IK_MAX_WORD = "ik_max_word";
	/** 最大分片数 */
	private static final int NUMBER_OF_SHARDS = 5;
	/** 数据备份数 */
	private static final int NUMBER_OF_REPLICAS = 1;

	@Override
	public boolean createWritingIndex() {
		if (this.existsIndex(EsEnum.INDEX_NAME)) {
			// 已存在，返回false
			return false;
		}
		RestHighLevelClient client = null;
		/*
		准备索引相关的setting
		确定分词器、最大分片数、数据备份数
		 */
		Settings.Builder settings = Settings.builder()
				.put("analysis.analyzer.default.type", ANALYZER_IK_MAX_WORD)
				.put("number_of_shards", NUMBER_OF_SHARDS)
				.put("number_of_replicas", NUMBER_OF_REPLICAS);

		try {
			// 准备关于索引结构的mappings
			XContentBuilder mappings = EsUtil.getEsWritingMappings();
			EsOperator operator = new EsOperator();
			client = EsUtil.getClient();
			CreateIndexResponse resp = operator.createIndex(client, settings, mappings, EsEnum.INDEX_NAME);
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
	public List<Long> checkWritingsExist(WritingType type, List<Long> writingsId) {
		/*
		之所以不在这里使用DAO获取writingIds，是因为这个EsService本质上不算数据库Service，
		而且也没有代理，没有处理异常情况和回滚
		 */
		List<Long> notExistIds = new ArrayList<>();
		EsOperator operator = new EsOperator();
		try {
			RestHighLevelClient client = EsUtil.getClient();
			// 遍历作品id，检查是不是所有的作品都在ES中，并返回存在的作品的id
			for (Long id : writingsId) {
				String docId = type.val() + id;
				boolean isExist = operator.existDoc(client, EsEnum.INDEX_NAME, docId);
				if (!isExist) {
					// 不存在
					notExistIds.add(id);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return notExistIds;
	}

	@Override
	public void initWritingDocs(WritingType type, List<Long> writingsId) throws Exception {
		// 获取所有的分类Json
		CategoryService categoryService = ServiceFactory.getArticleService();
		// Connection连接会在categoryService中关闭而引起bug
		JSONObject allCategory = categoryService.getAllCategory();

		Connection conn = JdbcUtil.beginTransaction();
		WritingDao<Article> dao = DaoFactory.getArticleDao();

		for (Long i : writingsId) {
			Article article = dao.getWritingById(conn, i);
			String categoryName = (String) allCategory.get(article.getCategory());
			assert categoryName != null;
//			String s = service.addDoc(EsEnum.INDEX_NAME, article, categoryName);
//			logger.debug(s);
		}
		JdbcUtil.closeTransaction();
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
	public String addDoc(String indexName, EsBo writing) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			// 获取文章的分类的字符串 要用到的service
			CategoryService categoryService = ServiceFactory.getCategoryService();

			// 封装实体数据到Json中
			JSONObject jsonObject = EsUtil.getJsonObject(writing);

			// 将Map存储到ES中
			EsOperator operator = new EsOperator();
			// 拼接ES的Id "article" + writingId
			String docId = writing.getWritingType() + writing.getWritingId();
			IndexResponse resp = operator.addDoc(client, jsonObject.toJSONString(), indexName, docId);

			//如果返回结果为CREATED，新增文档，如果返回结果是UPDATED，更新文档
			DocWriteResponse.Result result = resp.getResult();

			if (result == null || "".equals(result.toString())) {
				return ResultType.EXCEPTION.val();
			}
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
	public String updateDoc(String indexName, EsBo writing) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			// 封装实体数据到Json中
			JSONObject jsonObject = EsUtil.getJsonObject(writing);

			// 将Map存储到ES中
			EsOperator operator = new EsOperator();
			// 拼接ES的Id "article"/"posts" + writingId
			String docId = writing.getWritingType() + writing.getWritingId();
			UpdateResponse resp = operator.updateDoc(client, jsonObject.toJSONString(), indexName, docId);

			DocWriteResponse.Result result = resp.getResult();

			if (result == null || "".equals(result.toString())) {
				return ResultType.EXCEPTION.val();
			}
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
	public String bulkDoc(String indexName, String action, List<EsBo> docs) {
		BulkRequest request = new BulkRequest();
		// 封装request
		try {
			// 判断操作类型
			switch (action) {
				case "add":
					// 添加文档
					int add = 0;
					for (EsBo writing : docs) {
						JSONObject sourceMap = EsUtil.getJsonObject(writing);
						request.add(new IndexRequest(indexName).id(writing.getWritingId().toString())
								.source(XContentType.JSON, sourceMap.toJSONString())
						);
					}
					break;
				case "update":
					// 更新文档
					int up = 0;
					for (EsBo writing : docs) {
						JSONObject sourceMap = EsUtil.getJsonObject(writing);
						request.add(new UpdateRequest(indexName, writing.getWritingId().toString())
								.doc(XContentType.JSON, sourceMap.toJSONString())
						);
					}
					break;
				case "delete":
					// 删除文档
				default:
					for (EsBo writing : docs) {
						request.add(new DeleteRequest(indexName, writing.getWritingId().toString()));
					}
			}

			RestHighLevelClient client = EsUtil.getClient();
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

			// 获取结果日志
			String bulkRespLog = EsUtil.getBulkRespLog(bulkResponse);
			if (!ResultType.SUCCESS.val().equals(bulkRespLog)) {
				logger.error("=ElasticSearch bulk error=" + bulkRespLog);
			}

			return bulkRespLog;
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
	public void searchByHighLigh(String word, int from, int size) {
		String[] fieldNames = {"title", "content", "label1", "label2", "label3", "label4", "label5"};
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			SearchResponse resp =
					operator.mulitHighLightQuery(client, EsEnum.INDEX_NAME, fieldNames, word, from, size);


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
	}


}
