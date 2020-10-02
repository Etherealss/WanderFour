package common.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @description 数据库连接池工具类 —— Druid
 * @author 寒洲
 * @date 2020/7/24
 */
public class JdbcUtil {
    /**
     * 数据库DataSource
     */
    private static DataSource source = null;

    static{
        Logger log = Logger.getLogger(JdbcUtil.class);
        InputStream is = null;
        Properties pros;
        try {
            pros = new Properties();
            is = JdbcUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);

            log.info(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 数据库的连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    /**
     * 关闭数据库资源
     * @param conn 数据库连接
     * @param ps Statement对象
     * @param rs ResultSet对象
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭数据库资源，不关闭数据库连接
     * @param ps Statement对象
     * @param rs ResultSet对象
     */
    public static void closeResource(Statement ps, ResultSet rs){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     * @param conn 数据库连接
     */
    public static void closeConnection(Connection conn){
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
