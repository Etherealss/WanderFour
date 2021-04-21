package service.impl;


import com.alibaba.fastjson.JSONObject;
import common.enums.EsEnum;
import common.enums.ResultType;
import common.enums.WritingType;
import common.util.EsUtil;
import dao.ArticleDao;
import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.bo.EsBo;
import pojo.bo.PageBo;
import pojo.po.Article;
import common.others.EsOperator;
import service.EsService;

import java.io.IOException;
import java.util.ArrayList;
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

	@Autowired
	private ArticleDao articleDao;

	/** ik分词器 */
	private static final String ANALYZER_IK_MAX_WORD = "ik_max_word";
	/** 最大分片数 */
	private static final int NUMBER_OF_SHARDS = 5;
	/** 数据备份数 */
	private static final int NUMBER_OF_REPLICAS = 1;

	@Override
	public boolean createWritingIndex() {
		if (this.existsIndex(EsEnum.INDEX_NAME_WRITING)) {
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
			CreateIndexResponse resp = operator.createIndex(client, settings, mappings, EsEnum.INDEX_NAME_WRITING);
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
				String docId = (type.val() + id);
				boolean isExist = operator.existDoc(client, EsEnum.INDEX_NAME_WRITING, docId);
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
	public void initWritingDocs(WritingType type, List<Long> writingsId, JSONObject allCategory) throws Exception {
		for (Long i : writingsId) {
			Article article = articleDao.getWritingById(i);
			String categoryName = (String) allCategory.get(article.getCategory());
			assert categoryName != null;
			EsOperator operator = new EsOperator();
//			String s = operator.addDoc(EsEnum.INDEX_NAME_WRITING, article);
//			logger.debug(s);
		}
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

			// 封装实体数据到Json中
			JSONObject jsonObject = EsUtil.getJsonObjecrForEs(writing);

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
			JSONObject jsonObject = EsUtil.getJsonObjecrForEs(writing);

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
		if (docs == null || docs.size() == 0) {
			return "Nothing to add";
		}
		BulkRequest request = new BulkRequest();
		JSONObject sourceMap2 = null;
		// 封装request
		try {
			// 判断操作类型
			switch (action) {
				case EsEnum.ACTION_ADD:
					// 添加文档
					int add = 0;
					for (EsBo writing : docs) {
						JSONObject sourceMap = EsUtil.getJsonObjecrForEs(writing);
						sourceMap2 = sourceMap;
						request.add(new IndexRequest(indexName).id(writing.getWritingType() + writing.getWritingId())
								.source(sourceMap.toString(), XContentType.JSON)
						);
					}
					break;
				case EsEnum.ACTION_UPDATE:
					// 更新文档
					int up = 0;
					for (EsBo writing : docs) {
						JSONObject sourceMap = EsUtil.getJsonObjecrForEs(writing);
						request.add(new UpdateRequest(indexName, writing.getWritingType() + writing.getWritingId())
								.doc(sourceMap.toString(), XContentType.JSON)
						);
					}
					break;
				case EsEnum.ACTION_DELETE:
					// 删除文档
					for (EsBo writing : docs) {
						request.add(new DeleteRequest(indexName, writing.getWritingType() + writing.getWritingId()));
					}
					break;
				default:
					return "WRONG_ACTION";
			}

			RestHighLevelClient client = EsUtil.getClient();
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

			// 获取结果日志
			String bulkRespLog = EsUtil.getBulkRespLog(bulkResponse);
			if (!ResultType.SUCCESS.val().equals(bulkRespLog)) {
				logger.error("===================== ElasticSearch bulk error =====================\n"
						+ "index = " + indexName + ", action = " + action + "\n"
						+ bulkRespLog
						+ "Bo List :\n" + docs
				);
			}

			return bulkRespLog;
		} catch (Exception e) {
			e.printStackTrace();
			if (sourceMap2 != null) {
				logger.error("sourceJson = " + sourceMap2.toJSONString());
			} else {
				logger.error("sourceJson = null");
			}
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
	public PageBo<EsBo> searchByHighLigh(String word, int from, int size) {
		// 要高亮的字段
		/*
		{"title^3", "content", "categoryName^3", "label1^2",
				"label2^2", "label3^2", "label4^2", "label5^2"}
		 */
		Map<String, Float> fieldNames = new HashMap<>(10);
		fieldNames.put("title", 4.0F);
		fieldNames.put("content", 1.0F);
		fieldNames.put("categoryName", 3.0F);
		fieldNames.put("label1", 3.0F);
		fieldNames.put("label2", 3.0F);
		fieldNames.put("label3", 3.0F);
		fieldNames.put("label4", 3.0F);
		fieldNames.put("label5", 3.0F);


		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			SearchResponse resp =
					operator.mulitHighLightQuery(client, EsEnum.INDEX_NAME_WRITING, fieldNames, word, from, size);

			SearchHit[] hits = resp.getHits().getHits();

			String articleStr = WritingType.ARTICLE.val();
			String postsStr = WritingType.POSTS.val();

			List<EsBo> list = new ArrayList<>();

			for (SearchHit hit : hits) {
				EsBo es = getEsBoDataFromHit(hit);
				list.add(es);
			}

			// 包装
			PageBo<EsBo> pb = new PageBo<>();
			pb.setCurrentPage(from);
			pb.setRows(size);
			pb.setList(list);

			// 获取记录条数
			long totalCount = resp.getHits().getTotalHits().value;
			pb.setTotalCount(totalCount);

			/*
			计算总页码数
			如果总记录数可以整除以每页显示的记录数，
			那么总页数就是它们的商否则
			说明有几条数据要另开一页显示，总页数+1
			 */
			int page = (int) (totalCount / size);
			int totalPage = totalCount % size == 0 ? page : page + 1;
			pb.setTotalPage(totalPage);

			return pb;

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
	public List<EsBo> searchByPrefix(String word, int from, int size) {
		RestHighLevelClient client = null;
		try {
			client = EsUtil.getClient();

			EsOperator operator = new EsOperator();
			SearchResponse resp = operator.findByPrefix(client,
					EsEnum.INDEX_NAME_WRITING, "title", word, from, size);
			SearchHit[] hits = resp.getHits().getHits();

			List<EsBo> list = new ArrayList<>();
			for (SearchHit hit : hits) {
				EsBo es = getEsBoDataFromHit(hit);
				list.add(es);
			}

			return list;
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
	public List<String> querySuggestion(String word, String indexName, int size) {
		String fieldName_title = "title";
		RestHighLevelClient client = EsUtil.getClient();

		//设置搜索建议
		CompletionSuggestionBuilder suggestionBuilder = new CompletionSuggestionBuilder(
				fieldName_title).prefix(word).size(size);
		SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion(fieldName_title, suggestionBuilder);

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.suggest(suggestBuilder);

		//创建需要搜索的index
		SearchRequest request = new SearchRequest(indexName);
		request.source(builder);

		List<String> list = null;
		try {
			//进行搜索
			SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
			//用来处理的接受结果
			list = new ArrayList<>();

			List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>>
					entries = resp.getSuggest().getSuggestion(fieldName_title).getEntries();
			//处理结果
			for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> op : entries) {
				List<? extends Suggest.Suggestion.Entry.Option> options = op.getOptions();
				for (Suggest.Suggestion.Entry.Option pp : options) {
					list.add(pp.getText().toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 从SearchHit中获取Map，包装到实例中
	 * @param hit
	 * @return
	 */
	private static EsBo getEsBoDataFromHit(SearchHit hit) {
		String articleStr = WritingType.ARTICLE.val();
		String postsStr = WritingType.POSTS.val();
		Map<String, Object> source = hit.getSourceAsMap();
		String esId = hit.getId();
		if (esId.indexOf(articleStr) == 0) {
			// _id以"article"开头，是文章
			source.put("writingType", articleStr);
			source.put("writingId", Long.valueOf(esId.substring(articleStr.length())));
		} else {
			// 问贴
			source.put("writingType", postsStr);
			source.put("writingId", Long.valueOf(esId.substring(postsStr.length())));
		}
		String jsonStr = JSONObject.toJSONString(source);
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		EsBo es = jsonObject.toJavaObject(EsBo.class);
		return es;
	}
}
