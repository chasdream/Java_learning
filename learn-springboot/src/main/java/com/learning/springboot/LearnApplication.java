package com.learning.springboot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * <p>
 *  模拟springBoot中的SpringApplication.run()封装tomcat
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
        // web.xml两个类：ContextLoaderListener、DispatcherServlet
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8091);
            // 将项目加载到tomcat容器中, /boot1 相当于server.servlet.context-path=/boot1
            tomcat.addWebapp("/boot1", "/Users/idea/workspace/Java_learning/learn-springboot");
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
