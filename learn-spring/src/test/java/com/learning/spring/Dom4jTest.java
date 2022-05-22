package com.learning.spring;

import com.leraning.spring.context.AnnotationApplicationContext;
import com.leraning.spring.context.ClassPathXmlApplicationContext;
import com.leraning.spring.service.UserService;
import org.junit.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
public class Dom4jTest {

    @Test
    public void testGetBean() throws Exception {
        // 自定义一个ApplicationContext
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        context.refresh();

        UserService userService = (UserService) context.getBean("userService");
        assert null != userService;
        System.out.println(userService.getUser());
    }

    @Test
    public void testInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("com.leraning.spring.service.impl.UserServiceImpl");
        Object obj = aClass.newInstance();
        System.out.println(obj);
    }

    @Test
    public void testAnnotationGetBean() {
        AnnotationApplicationContext context = new AnnotationApplicationContext();
        context.refresh();

        UserService userService = (UserService) context.getBean("userService");
        assert null != userService;
        System.out.println(userService.getUser());
    }
}
