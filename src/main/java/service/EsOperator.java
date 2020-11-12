package service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/12
 */
public class EsOperator {

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
	public SearchResponse findByPrefix(RestHighLevelClient client, String indexName, String fieldName, String prefix, int from, int size) throws IOException {
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
	public SearchResponse findByFuzzy(RestHighLevelClient client, String indexName, String fieldName, String value, int from, int size) throws IOException {
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
	public SearchResponse findByFuzzy(RestHighLevelClient client, String indexName, String fieldName, String value, int from, int size, int prefixLength) throws IOException {
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
	public SearchResponse findByRegexp(RestHighLevelClient client, String indexName, String fieldName, String exp, String value) throws IOException {
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
	public SearchResponse findByRange(RestHighLevelClient client, String indexName, String fieldName, int from, int size, int lt, int gt) throws IOException {
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
	public SearchResponse termQuery(RestHighLevelClient client, String indexName, String fieldName, String value, int from, int size) throws IOException {
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
	public SearchResponse termsQuery(RestHighLevelClient client, String indexName, String fieldName, int from, int size, String... values) throws IOException {
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
	public SearchResponse matchQuery(RestHighLevelClient client, String indexName, String fieldName, String value, int from, int size) throws IOException {
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
	public SearchResponse matchQuery(RestHighLevelClient client, String indexName, String fieldName, String value, int from, int size, Operator operator) throws IOException {
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
	public SearchResponse scrollQuery(RestHighLevelClient client, String indexName, String fieldName, int size, SortOrder sortOrder, long timeOut) throws IOException {
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

}
