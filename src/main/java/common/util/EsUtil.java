package common.util;

import com.alibaba.fastjson.JSONObject;
import common.enums.ApplicationConfig;
import common.enums.ResultType;
import common.others.EsProcessManager;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.bo.EsBo;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * @author 寒洲
 * @description ES工具类
 * @date 2020/11/10
 */
public abstract class EsUtil {

    private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    private static final String ES_HOST = ApplicationConfig.ES_HOST;
    private static final int ES_PORT = ApplicationConfig.ES_PORT;

    /**
     * @return ES启动路径，即elasticsearch.bat文件路径
     */
    public static String getEsPath() {
        return ApplicationConfig.ES_PATH;
    }

    /**
     * 启动ES服务
     * @return
     */
    public static boolean runEsService() {
        boolean isEsHostConnected = EsUtil.isEsHostConnected();
        // 未启动而且是windows环境（windows环境代表本地环境而不是服务器环境，可以手动开启服务）
        if (!isEsHostConnected && OsUtil.isWindows()) {
            logger.info("启动ES");
            // 获取elasticsearch.bat文件路径，通过Runtime.getRuntime().exec()方法启动
            try {
                Process process = FileUtil.runProcess(EsUtil.getEsPath());
                // 启动后尝试连接ES
                if (process != null && EsUtil.isEsHostConnected()) {
                    EsProcessManager.setEsProcess(process);
                    return true;
                } else {
                    logger.warn("ES启动失败");
                    return false;
                }
            } catch (Exception e) {
                logger.error("elasticsearch.bat启动异常：", e);
            }
        } else {
            logger.info("ES已就绪");
            return true;
        }
        return false;
    }

    /**
     * 连接
     * @return
     */
    public static RestHighLevelClient getClient() {
        HttpHost httpHost = new HttpHost(ES_HOST, ES_PORT);
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
            socket.connect(new InetSocketAddress(ES_HOST, ES_PORT));
        } catch (Exception e) {
            // 不打印Exception信息
            return false;
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
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
                .startObject("title").field("type", "completion").endObject()
                .startObject("authorId").field("type", "long").endObject()
                .startObject("content").field("type", "text").endObject()
                .startObject("categoryId").field("type", "integer").endObject()
                .startObject("categoryName").field("type", "keyword").endObject()
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

    private static final String[] ES_FIELD_NAMES = {
            "authorId", "title", "content", "categoryId", "categoryName", "createTime",
            "updateTime", "liked", "collected", "label1", "label2", "label3", "label4", "label5",
            "liked", "collected"
    };

    /**
     * 将实体类的属性封装到Map中
     * @param writing
     * @return
     */
    public static JSONObject getJsonObjecrForEs(EsBo writing) throws Exception {
        JSONObject jsonMap = new JSONObject();
//		jsonMap.put("writing_id", writing.getId());
        // 因为文章和评论的id可能会重复，且放在同一个索引下
        // 所以这里添加两个参数 作品类型 及其id
//		jsonMap.put("writing_type", writing.getWritingType());

        jsonMap.put("authorId", writing.getAuthorId());
        jsonMap.put("title", writing.getTitle());
        jsonMap.put("content", writing.getContent());

        jsonMap.put("categoryId", writing.getCategoryId());
        jsonMap.put("categoryName", writing.getCategoryName());

        jsonMap.put("createTime", writing.getCreateTime());
        jsonMap.put("updateTime", writing.getUpdateTime());
        jsonMap.put("liked", writing.getLiked());
        jsonMap.put("collected", writing.getCollected());

        String[] allLabels = writing.getAllLabels();
        int i = 1;
        for (String label : allLabels) {
            if (label != null && !"".equals(label)) {
                jsonMap.put("label" + i++, label);
            }
        }

        return jsonMap;
    }

    /**
     * 通过Map&lt;String, Object>获取实例
     * @param source
     * @return
     */
    public static EsBo getEsBoByMap(Map<String, Object> source) {
        EsBo res;
        try {
            Class<?> esBoClass = Class.forName("pojo.bo.EsBo");
            res = (EsBo) esBoClass.newInstance();
        } catch (Exception e) {
            logger.error("通过Map获取实例失败：类实例化异常");
            return null;
        }

        // 根据Map数据确定需要set的属性
        Set<String> keySet = source.keySet();
        for (String key : keySet) {
            ObjectUtil.invokeSetter(res, key, source.get(key));
        }

        return res;
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
