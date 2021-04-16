package common.enums;

import common.util.JedisUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wtk
 * @description
 * @date 2021-04-15
 */
public class ApplicationConfig {
    private static Logger logger = Logger.getLogger(ApplicationConfig.class);

    public static final String CODING_FORMAT = "UTF-8";

    /** 头像储存目录位置 */
    public static final String AVATAR_PATH;
    public static final String AVATAR_DEFAULT_PATH_BOY;
    public static final String AVATAR_DEFAULT_PATH_GIRL;

    /** elasticsearch主机号 */
    public static final String ES_HOST;
    /** elasticsearch端口号 */
    public static final int ES_PORT;
    /** elasticsearch.bat文件路径 */
    public static final String ES_PATH;

    static {
        //获取配置文件
        InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("ApplicationConfig.properties");
        Properties prop = new Properties();
        try {
            //读取
            prop.load(is);
        } catch (Exception e) {
            logger.fatal("初始化参数失败", e);
        }
        // elasticsearch
        ES_HOST = prop.getProperty("es.host");
        ES_PORT = Integer.parseInt(prop.getProperty("es.port"));
        ES_PATH = prop.getProperty("es.path");

        // 头像
        AVATAR_PATH = prop.getProperty("avatar.directory");
        AVATAR_DEFAULT_PATH_BOY = AVATAR_PATH + "default-boy.png";
        AVATAR_DEFAULT_PATH_GIRL = AVATAR_PATH + "default-girl.png";

        // 检查配置参数指向的文件或目录是否存在
        checkPathExists();
    }

    /**
     * 检查配置参数指向的文件或目录是否存在
     */
    private static void checkPathExists() {
        checkDirExists(ES_PATH);
        checkDirExists(AVATAR_PATH);
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
            logger.fatal("目录或文件不存在：" + file.getName());
        }
    }

}
