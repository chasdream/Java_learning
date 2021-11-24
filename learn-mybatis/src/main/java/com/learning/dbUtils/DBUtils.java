package com.learning.dbUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class DBUtils {

    private static HikariDataSource hikariDataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mysql_learning");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("123456");
        // 等待连接池分配连接的最大时长（单位：毫秒），缺省30秒
        config.setConnectionTimeout(30000);
        // 一个连接idle状态的最大时长
        config.setIdleTimeout(600000);
        // 连接池中允许最大的连接数，推荐公式：((core_count * 2) + effective_spindle_count)
        config.setMaximumPoolSize(15);
        // 一个连接的生命时长(单位：毫秒)，超时且没被使用则释放，默认30分钟，建议设置比数据库超时(mysql wait_timeout)时长少30秒
        config.setMaxLifetime(1800000);

        // 创建数据源
        hikariDataSource = new HikariDataSource(config);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static DataSource getDataSource() {
        return hikariDataSource;
    }
}
