package service;

import org.apache.log4j.Logger;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.List;

/**
 * @author 寒洲
 * @description ES 查询API操作
 * @date 2020/11/12
 */
public class EsSearchOperator {

	private Logger logger = Logger.getLogger(EsSearchOperator.class);

	/**
	 * 根据id查询
	 * @param client
	 * @param indexName
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public GetResponse findById(RestHighLevelClient client, String indexName, String id) throws IOException {
		GetRequest request = new GetRequest(indexName, id);
		return client.get(request, RequestOptions.DEFAULT);
	}


	/**
	 * 根据多个id查询
	 * @param client
	 * @param indexName
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByIds(RestHighLevelClient client, String indexName, String... ids) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(QueryBuilders.idsQuery().addIds(ids));

		request.source(builder);
		return client.search(request, RequestOptions.DEFAULT);

	}


	/**
	 * 前缀查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param prefix
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByPrefix(RestHighLevelClient client, String indexName, String fieldName,
	                                   String prefix, int from, int size) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		builder.query(QueryBuilders.prefixQuery(fieldName, prefix));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 模糊查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByFuzzy(RestHighLevelClient client, String indexName, String fieldName,
	                                  String value, int from, int size) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		builder.query(QueryBuilders.fuzzyQuery(fieldName, value));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 模糊查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param prefixLength 指定前prefixLength个字符不能错误中
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByFuzzy(RestHighLevelClient client, String indexName, String fieldName,
	                                  String value, int from, int size, int prefixLength) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		// 指定前prefixLength个字符不能错误
		builder.query(QueryBuilders.fuzzyQuery(fieldName, value).prefixLength(prefixLength));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 正则查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param exp
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByRegexp(RestHighLevelClient client, String indexName, String fieldName,
	                                   String exp, String value) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = new SearchSourceBuilder();
		// 指定前prefixLength个字符不能错误
		builder.query(QueryBuilders.regexpQuery(fieldName, exp));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}


	/**
	 * 范围查询
	 * - gte：大于等于		gt：大于
	 * - lte：小于等于 		lt：小于
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param lt 小于
	 * @param gt 大于
	 * @return
	 * @throws IOException
	 */
	public SearchResponse findByRange(RestHighLevelClient client, String indexName, String fieldName,
	                                  int from, int size, int lt, int gt) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		// gt：大于  lt：小于
		builder.query(QueryBuilders.rangeQuery(fieldName).lt(lt).gt(gt));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * term查询是完全匹配查询，搜索不会对搜索的关键词进行分词
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse termQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                String value, int from, int size) throws IOException {
		SearchRequest request = new SearchRequest(indexName);

		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		builder.query(QueryBuilders.termQuery(fieldName, value));

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}


	/**
	 * 根据多个查询条件查询对应字段，不分词
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param values
	 * @return
	 * @throws IOException
	 */
	public SearchResponse termsQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                 int from, int size, String... values) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		// 封装查询条件
		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		builder.query(QueryBuilders.termsQuery(fieldName, values));
		request.source(builder);
		// 执行 获取结果
		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 搜索所有
	 * @param client
	 * @param indexName
	 * @return
	 * @throws IOException
	 */
	public SearchResponse matchAllQuery(RestHighLevelClient client, String indexName) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		// 封装查询条件
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchAllQuery());

		request.source(builder);
		// 执行 获取结果
		return client.search(request, RequestOptions.DEFAULT);
	}


	/**
	 * match属于高层查询，它会根据你查询字段的类型不一样，采取不同的查询方式。
	 * <p>
	 * - 如果查询的是日期或者数值，它会将字符串查询内容转换为日期或者数值对待
	 * - 如果查询的内容是一个不能被分词的内容(keyword)，match查询不会对你指定的查询关键字分词
	 * - 如果查询的内容是一个可以被分词的内容(text)，macth会将你指定的查询内容根据一定方式去分词
	 * <p>
	 * match查询底层是多个term查询，只是它拼接了多个term查询并封装在一起
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse matchQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                 String value, int from, int size) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		// 封装查询条件
		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		builder.query(QueryBuilders.matchQuery(fieldName, value));
		request.source(builder);
		// 执行 获取结果
		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * match属于高层查询，它会根据你查询字段的类型不一样，采取不同的查询方式。
	 * <p>
	 * - 如果查询的是日期或者数值，它会将字符串查询内容转换为日期或者数值对待
	 * - 如果查询的内容是一个不能被分词的内容(keyword)，match查询不会对你指定的查询关键字分词
	 * - 如果查询的内容是一个可以被分词的内容(text)，macth会将你指定的查询内容根据一定方式去分词
	 * <p>
	 * match查询底层是多个term查询，只是它拼接了多个term查询并封装在一起
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param from
	 * @param size
	 * @param value
	 * @param operator  and查询 / or查询
	 * @return
	 * @throws IOException
	 */
	public SearchResponse matchQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                 String value, int from, int size, Operator operator) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		// 封装查询条件
		SearchSourceBuilder builder = getSearchSourceBuilder(from, size);
		// 根据operator查询
		builder.query(QueryBuilders.matchQuery(fieldName, value).operator(operator));
		request.source(builder);
		// 执行 获取结果
		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 获取封装的from和size的Builder
	 * @param from
	 * @param size
	 * @return
	 */
	private SearchSourceBuilder getSearchSourceBuilder(int from, int size) {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.from(from);
		builder.size(size);
		return builder;
	}

	/**
	 * 初始化Scroll
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param size 记录数
	 * @param sortOrder 排序方式
	 * @param timeOut 超时时间
	 * @return
	 * @throws IOException
	 */
	public SearchResponse scrollQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                  int size, SortOrder sortOrder, long timeOut) throws IOException {
		SearchRequest request = new SearchRequest(indexName);
		request.scroll(TimeValue.timeValueMinutes(timeOut));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.size(size);
		builder.sort(fieldName, sortOrder);
		builder.query(QueryBuilders.matchAllQuery());

		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 获取Scroll下一页的数据
	 * @param client
	 * @param scrollId
	 * @param timeValue
	 * @return
	 * @throws IOException
	 */
	public SearchResponse nexrScrollQuery(RestHighLevelClient client, String scrollId, long timeValue) throws IOException {
		SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
		scrollRequest.scroll(TimeValue.timeValueMinutes(timeValue));
		return client.scroll(scrollRequest, RequestOptions.DEFAULT);
	}

	/**
	 * 清除Scroll上下文缓存
	 * @param client
	 * @param scrollId
	 * @return
	 * @throws IOException
	 */
	public boolean clearScrollQuery(RestHighLevelClient client, String scrollId) throws IOException {
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollId);

		ClearScrollResponse resp = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		return resp.isSucceeded();
	}

	/**
	 * 以range的方式查询并删除文档
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param lt
	 * @throws IOException
	 */
	public void deleteByRangeQuery(RestHighLevelClient client, String indexName, String fieldName, int lt) throws IOException{
		DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);

		//查询要删除的文档有多种方式,，此处为range
		request.setQuery(QueryBuilders.rangeQuery(fieldName).lt(lt));

		client.deleteByQuery(request, RequestOptions.DEFAULT);
	}

	/**
	 * 布尔查询
	 * @param client
	 * @param indexName
	 * @return
	 * @throws IOException
	 */
	public SearchResponse boolQuery(RestHighLevelClient client, String indexName) throws IOException{
		SearchRequest request = new SearchRequest(indexName);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		//或
		boolQueryBuilder.should(QueryBuilders.termQuery("title", "数学"));

		//非
		boolQueryBuilder.mustNot(QueryBuilders.termQuery("title", "语文"));

		//与
		boolQueryBuilder.must(QueryBuilders.matchQuery("content", "学习"));

		builder.query(boolQueryBuilder);
		request.source(builder);
		return client.search(request, RequestOptions.DEFAULT);

	}

	/**
	 * boosting查询
	 * @param client
	 * @param indexName
	 * @return
	 * @throws IOException
	 */
	public SearchResponse boostingQuery(RestHighLevelClient client, String indexName, int negativeBoost) throws IOException{

		BoostingQueryBuilder boostingQueryBuilder = QueryBuilders.boostingQuery(
				QueryBuilders.matchQuery("content", "数学"),
				QueryBuilders.matchQuery("content", "语文")
		).negativeBoost(negativeBoost);

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(boostingQueryBuilder);

		SearchRequest request = new SearchRequest(indexName);
		request.source(builder);
		return client.search(request, RequestOptions.DEFAULT);

	}

	/**
	 * filter查询不会计算分数score，不做排序，但是会缓存
	 * filter性能高于普通的query
	 * @param client
	 * @param indexName
	 * @return
	 * @throws IOException
	 */
	public SearchResponse filter(RestHighLevelClient client, String indexName) throws IOException {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.filter(QueryBuilders.termQuery("content", "数学"));
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("fee").lte(5));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(boolQueryBuilder);

		SearchRequest request = new SearchRequest(indexName);
		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}


	/**
	 * 高亮查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public SearchResponse highLightQuery(RestHighLevelClient client, String indexName, String fieldName,
	                                     String value) throws IOException {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchQuery(fieldName, value));

		int fragmentSize = 10;
		HighlightBuilder highlightBuilder = new HighlightBuilder();

		// 同时指定高亮部分的前后标签
		highlightBuilder.field(fieldName, fragmentSize)
				.preTags("<font color='red'>")
				.postTags("</font>");
		builder.highlighter(highlightBuilder);

		SearchRequest request = new SearchRequest(indexName);
		request.source(builder);

		return client.search(request, RequestOptions.DEFAULT);
	}

	/**
	 * 去重聚合查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public long cardinality(RestHighLevelClient client, String indexName, String fieldName, String value) throws IOException {
		String aggName = "aggName";
		SearchRequest request = new SearchRequest(indexName);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.aggregation(AggregationBuilders.cardinality(aggName).field(fieldName));

		request.source(builder);

		//获取去重计数的结果
		SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
		//Cardinality向下转型
		Cardinality aggregation = resp.getAggregations().get(aggName);
		long count = aggregation.getValue();
		return count;

	}

	/**
	 * 区间聚合查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public Range range(RestHighLevelClient client, String indexName, String fieldName, String value) throws IOException {
		String aggName = "aggName";

		SearchRequest request = new SearchRequest(indexName);
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.aggregation(
				AggregationBuilders.range(aggName).field("createTime")
				.addUnboundedTo(5)
				.addRange(5, 10)
				.addUnboundedFrom(10)
		);

		request.source(builder);

		SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
		Range aggs = resp.getAggregations().get(aggName);
		List<? extends Range.Bucket> buckets = aggs.getBuckets();
		for (Range.Bucket bucket : buckets) {
			String key = bucket.getKeyAsString();
			Object from = bucket.getFrom();
			Object to = bucket.getTo();
			long docCount = bucket.getDocCount();
			logger.info("key:" + key + ", from:" + from + ", to:" + to + ", count:" + docCount);
		}
		return aggs;
	}

	/**
	 * 统计聚合查询
	 * @param client
	 * @param indexName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public ExtendedStats extendedStats(RestHighLevelClient client, String indexName, String fieldName,
	                                   String value) throws IOException {
		String aggName = "aggName";
		SearchRequest request = new SearchRequest(indexName);
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.aggregation(AggregationBuilders.extendedStats(aggName).field("fee"));
		request.source(builder);

		//获取去重计数的结果
		SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
		//Cardinality向下转型
		ExtendedStats aggs = resp.getAggregations().get(aggName);
		double max = aggs.getMax();
		double min = aggs.getMin();

		return aggs;
	}
}
