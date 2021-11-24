package com.learning.springboot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/12
 */
public class LearnApplication {

    public static void run() {
        // 1.整合springmvc
        // 2.内置了tomcat
        // 3.去除了xml
        // web.xml两个类：ContextListener、DispatcherServlet
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8090);
            tomcat.addWebapp("/boot1", "/Users/idea/workspace/java-learn/learn-springboot"); // 增加项目路径
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
