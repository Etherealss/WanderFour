package common.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/10
 */
public class EsUtil {

	/**
	 * 连接
	 * @return
	 */
	public static RestHighLevelClient getClient() {
		HttpHost httpHost = new HttpHost("localhost", 9200);
		RestClientBuilder clientBuilder = RestClient.builder(httpHost);
		return new RestHighLevelClient(clientBuilder);
	}


}
