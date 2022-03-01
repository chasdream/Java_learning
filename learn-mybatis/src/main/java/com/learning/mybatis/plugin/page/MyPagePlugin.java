package com.learning.mybatis.plugin.page;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 官方插件开发方式，
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
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        // 从invocation对象中获取StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        // 获取boundSql对象中的原始sql语句
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println("原sql = " + sql);

        // 通过BoundSql对象获取分页参数
        Object parameterObject = boundSql.getParameterObject();

        // 将statementHandler转换成MetaObject对象
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // 获取MappedStatement对象
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 获取Mapper接口中方法名，如：package.selectPage()等;
        String mapperMethodName = mappedStatement.getId();

        Map<String, Object> params = (Map<String, Object>) parameterObject;
        // 获取分页对象，在调用插件的时候将插件对象放到parameterObject中：params.put("pageInfo", new PageInfo());
        PageInfo pageInfo = (PageInfo) params.get("pageInfo");

        // 查询总数，一个sqlSession会话只会执行一条sql，因此查询总数查询通过jdbc等其他方式来查询
        String totalCountSql = "select count(1) from (" + sql + ") c";
        System.out.println("查询总数sql = " + totalCountSql);

        // todo jdbc操作查询记录总数

        // 改造原始sql，加上limit分页
        String pageSql = sql + " limit " + pageInfo.getStart() + ", " + pageInfo.getPageSize();
        System.out.println("分页sql = " + pageSql);

        // 将分页sql覆盖原sql并交给mybatis执行
        metaObject.setValue("delegate.boundSql.sql", pageSql);

        // 把流程逻辑交给mybatis
        return invocation.proceed();
    }

    // 使用mybatis官方推荐的插件Plugin执行
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    // 设置属性
    @Override
    public void setProperties(Properties properties) {
        // 此处可以进行数据元的自定义，比如利用数据库方言或数据库类型
    }
}
