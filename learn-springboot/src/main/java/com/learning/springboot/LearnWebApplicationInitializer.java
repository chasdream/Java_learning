package com.learning.springboot;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/12
 */
public class LearnWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        context.setServletContext(servletContext);
        context.refresh();

        DispatcherServlet servlet = new DispatcherServlet(context); // 创建容器，子容器
        ServletRegistration.Dynamic app = servletContext.addServlet("app", servlet);
        app.setLoadOnStartup(1);
        app.addMapping("/");
    }
}
