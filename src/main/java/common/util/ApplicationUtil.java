package common.util;

import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wtk
 * @description
 * @date 2021-04-23
 */
public class ApplicationUtil {

    /**
     * 关闭对象
     * @param closeable 要关闭的对象
     */
    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    /**
     * 关闭对象
     * @param closeable 要关闭的对象
     * @param logger 日志
     */
    public static void close(Closeable closeable, Logger logger) {
        try {
            close(closeable);
        } catch (IOException e) {
            logger.warn("对象close错误:" + closeable.getClass().getName(), e);
        }
    }

}
