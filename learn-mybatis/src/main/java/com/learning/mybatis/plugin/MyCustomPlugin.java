package com.learning.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * 自定义插件开发，和官方推荐的插件开发方式不同点：
 * 1.自定义插件开发方式不需要@Intercepts注解，官方插件开发方式需要增加@Intercepts注解；
 * 2.自定义插件开发方式需要重写public Object plugin(Object target)方法，
 * 官方插件开发方式只需要调用Plugin.wrap(target, this)即可；
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/2/26
 */
//@Intercepts({@Signature(type = Executor.class, method = "query", args = {
//        MappedStatement.class,
//        Object.class,
//        RowBounds.class,
//        ResultHandler.class})})
public class MyCustomPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 被代理的对象
        Object target = invocation.getTarget();
        // 代理对象的执行目标方法
        Method method = invocation.getMethod();
        // 代理对象执行目标方法的参数
        Object[] args = invocation.getArgs();

        // do something 方法拦截前执行代码快
        Object result = invocation.proceed();
        // do something 方法拦截后执行的代码快
        return result;
    }

    @Override
    public Object plugin(Object target) {
//        // 调用官方提供的插件执行器
//        return Plugin.wrap(target, this);
        // 自定义执行插件逻辑，利用动态代理
        return Proxy.newProxyInstance(Interceptor.class.getClassLoader(),
                target.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return intercept(new Invocation(target, method, args));
                    }
                });
    }

    @Override
    public void setProperties(Properties properties) {
        /*
        设置属性:
        <!--配置自定义插件-->
        <plugins>
            <plugin interceptor="com.learning.mybatis.plugin.MyPagePlugin">
                <property name="type" value="mysql"/>
            </plugin>
        </plugins>
         */
        String type = properties.getProperty("type");
        System.out.println("type = " + type);
    }
}
