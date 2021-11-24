package com.learning.mybatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
@Intercepts(
        @Signature(
                type = StatementHandler.class, // 插件生效的位置
                method = "prepare", // 插件生效的方法
                args = {Connection.class, Integer.class}
        )
)
public class MyPagePlugin implements Interceptor {

    // 插件核心业务逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
