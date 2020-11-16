package common.util;

import com.alibaba.fastjson.JSONObject;
import common.enums.ResultType;
import common.others.EsProcessManager;
import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import pojo.bo.EsBo;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

/**
 * @author 寒洲
 * @description ES工具类
 * @date 2020/11/10
 */
public abstract class EsUtil {

	private static Logger logger = Logger.getLogger(EsUtil.class);

	private static String esHost;
	private static int esPort;
	/** elasticsearch.bat文件路径 */
	private static String esPath;

	public static String getEsPath() {
		return esPath;
	}

	static {
		initEs();
	}

	/**
	 * 启动ES服务
	 * @return
	 */
	public static boolean runEsService(){
		boolean isEsHostConnected = EsUtil.isEsHostConnected();
		if (!isEsHostConnected) {
			logger.info("启动ES");
			// 获取elasticsearch.bat文件路径，通过Runtime.getRuntime().exec()方法启动
			try {
				Process process = FileUtil.runProcess(EsUtil.getEsPath());
				if (process != null){
					EsProcessManager.setEsProcess(process);
					return true;
				}
			} catch (IOException e) {
				logger.error("==== elasticsearch.bat启动失败：" + e.getMessage() + " ====");
			}
		}
		return false;
	}

	/**
	 * 配置ES
	 */
	private static void initEs() {
		//获取配置文件
		InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("elastic_search.properties");
		Properties prop = new Properties();
		try {
			//读取
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置
		esHost = prop.getProperty("host");
		esPort = Integer.parseInt(prop.getProperty("port"));
		esPath = prop.getProperty("es_path");
	}


	/**
	 * 连接
	 * @return
	 */
	public static RestHighLevelClient getClient() {
		HttpHost httpHost = new HttpHost(esHost, esPort);
		RestClientBuilder clientBuilder = RestClient.builder(httpHost);
		return new RestHighLevelClient(clientBuilder);
	}


	/**
	 * 判断端口是否可连接
	 * @return
	 */
	public static boolean isEsHostConnected() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(esHost, esPort));
		} catch (IOException e) {
			// 不打印Exception信息
			return false;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * @return 作品的ES mapping
	 * @throws IOException
	 */
	public static XContentBuilder getEsWritingMappings() throws IOException {
		XContentBuilder mappings = JsonXContent.contentBuilder()
				.startObject()
				.startObject("properties")
//				.startObject("writing_id").field("type", "integer").endObject()
//				.startObject("writing_type").field("type", "keyword").endObject()
				.startObject("title").field("type", "text").endObject()
				.startObject("authorId").field("type", "integer").endObject()
				.startObject("content").field("type", "text").endObject()
				.startObject("category_id").field("type", "integer").endObject()
				.startObject("category_name").field("type", "keyword").endObject()
				.startObject("label1").field("type", "keyword").endObject()
				.startObject("label2").field("type", "keyword").endObject()
				.startObject("label3").field("type", "keyword").endObject()
				.startObject("label4").field("type", "keyword").endObject()
				.startObject("label5").field("type", "keyword").endObject()
				.startObject("createTime").field("type", "date").endObject()
				.startObject("updateTime").field("type", "date").endObject()
				.startObject("liked").field("type", "integer").endObject()
				.startObject("collected").field("type", "integer").endObject()
				.endObject()
				.endObject();
		return mappings;
	}

	/**
	 * 将实体类的属性封装到Map中
	 * @param writing
	 * @return
	 */
	public static JSONObject getJsonObject(EsBo writing) throws Exception {
		JSONObject jsonMap = new JSONObject();
//		jsonMap.put("writing_id", writing.getId());
		// 因为文章和评论的id可能会重复，且放在同一个索引下
		// 所以这里添加两个参数 作品类型 及其id
//		jsonMap.put("writing_type", writing.getWritingType());

		jsonMap.put("athorId", writing.getAuthorId());
		jsonMap.put("title", writing.getTitle());
		jsonMap.put("content", writing.getContent());

		jsonMap.put("category_id", writing.getCategoryId());
		jsonMap.put("category_name", writing.getCategoryName());

		jsonMap.put("label1", writing.getLabel1());
		jsonMap.put("label2", writing.getLabel2());
		jsonMap.put("label3", writing.getLabel3());
		jsonMap.put("label4", writing.getLabel4());
		jsonMap.put("label5", writing.getLabel5());
		jsonMap.put("create_time", writing.getCreateTime());
		jsonMap.put("update_time", writing.getUpdateTime());
		jsonMap.put("liked", writing.getLiked());
		jsonMap.put("collected", writing.getCollected());
		return jsonMap;
	}

	/**
	 * 确定Writing的类型
	 * @param writing
	 * @param <T>
	 * @return
	 */
	public static <T extends Writing> String getWritingTypeStr(T writing) {
		if (writing instanceof Article) {
			return "article";
		} else {
			assert writing instanceof Posts;
			return "posts";
		}
	}


	/**
	 * 获取ES操作结果日志
	 * @param bulkResponse
	 * @return 结果日志，如果操作全部成功，返回"SUCCESS"
	 */
	public static String getBulkRespLog(BulkResponse bulkResponse) {
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
			return sb.toString();
		} else {
			return ResultType.SUCCESS.val();
		}
	}

}
