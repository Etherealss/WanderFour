package common.enums;

import common.util.JedisUtil;
import common.util.OsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wtk
 * @description
 * @date 2021-04-15
 */
public class ApplicationConfig {

    private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    public static final String CODING_FORMAT = "UTF-8";

    /** 头像储存目录位置 */
    public static final String AVATAR_DIR;
    public static final String AVATAR_DEFAULT_PATH_BOY;
    public static final String AVATAR_DEFAULT_PATH_GIRL;

    /** elasticsearch主机号 */
    public static final String ES_HOST;
    /** elasticsearch端口号 */
    public static final int ES_PORT;
    /** elasticsearch.bat文件路径 */
    public static final String ES_PATH;
    /** 是否在关闭程序时关闭ES服务 */
    public static final boolean ES_SHUTDOWN;

    /** 点赞记录的记录间隔（分钟） */
    public static final int PERIOD_LIKE_PERSISTENCE;

    static {
        //获取配置文件
        InputStream is = ApplicationConfig.class.getClassLoader().getResourceAsStream("ApplicationConfig.properties");
        Properties prop = new Properties();
        try {
            //读取
            prop.load(is);
        } catch (Exception e) {
            logger.error("初始化参数失败", e);
        }

        ES_HOST = prop.getProperty("es.host");
        ES_PORT = Integer.parseInt(prop.getProperty("es.port"));

        // 点赞记录的记录间隔（分钟）
        PERIOD_LIKE_PERSISTENCE = Integer.parseInt(prop.getProperty("period.likePersistencebyMinutes"));

        if (OsUtil.isWindows()) {
            ES_PATH = prop.getProperty("es.path_windows");
            ES_SHUTDOWN = "1".equals(prop.getProperty("es.shutDown"));
            AVATAR_DIR = prop.getProperty("avatar.directory_windows");
        } else {
            ES_PATH = prop.getProperty("es.path_linux");
            ES_SHUTDOWN = false;
            AVATAR_DIR = prop.getProperty("avatar.directory_linux");
        }

        AVATAR_DEFAULT_PATH_BOY = AVATAR_DIR + "default-boy.png";
        AVATAR_DEFAULT_PATH_GIRL = AVATAR_DIR + "default-girl.png";

        // 检查配置参数指向的文件或目录是否存在
        checkPathExists();
    }

    /**
     * 检查配置参数指向的文件或目录是否存在
     */
    private static void checkPathExists() {
        checkDirExists(ES_PATH);
        checkDirExists(AVATAR_DIR);
        checkDirExists(AVATAR_DEFAULT_PATH_BOY);
        checkDirExists(AVATAR_DEFAULT_PATH_GIRL);
    }

    /**
     * 检查文件或目录是否存在
     * @param path 路径
     */
    private static void checkDirExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("目录或文件不存在：" + file.getName());
        }
    }

}
