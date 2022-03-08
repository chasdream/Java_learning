package com.learning.springboot;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * <p>
 * 通过SPI机制加载：/spring-web-5.2.6.RELEASE.jar!/META-INF/services/javax.servlet.ServletContainerInitializer
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/12
 */
public class LearnWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        // 创建spring容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        context.setServletContext(servletContext);
        context.refresh();

        // 创建和注册DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(context); // 创建容器，子容器
        ServletRegistration.Dynamic app = servletContext.addServlet("app", servlet);
        app.setLoadOnStartup(1);
        app.addMapping("/"); // 拦截的请求地址，拦截所有请求
    }
}
