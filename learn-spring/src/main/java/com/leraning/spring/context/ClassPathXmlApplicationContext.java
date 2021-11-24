package com.leraning.spring.context;

import com.leraning.spring.utils.Dom4jUtils;
import org.dom4j.DocumentException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  加载并实例化xml中bean的配置信息
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
public class ClassPathXmlApplicationContext {

    // xml文件路径
    private final String xmlPath;

    // 存放bean实例缓存
    private static final Map<String, Object> singletObjects = new ConcurrentHashMap<>();

    /**
     * 构造xml文件路径
     *
     * @param xmlPath 文件路径
     */
    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     * 获取bean对象
     *
     * @param id
     * @return
     * @throws IllegalAccessException
     */
    public Object getBean(String id) throws IllegalAccessException {
        // id为空，抛出异常
        if (id == null) {
            throw new IllegalAccessException("id is not null");
        }
        // 缓存中包含id，返回对应bean的对象
        if (singletObjects.containsKey(id)) {
            return singletObjects.get(id);
        }
        // 否则进行，抛出未找到bean（注：此处可以进行进行bean的实例化）
        return null;
    }

    /**
     * 解析xml文件，并实例化bean对象
     */
    public void refresh() {
        try {
            Map<String, String> elements = Dom4jUtils.readerXml(xmlPath);

            // 解析map中的key-value，通过反射实例化bean
            for (Map.Entry<String, String> entry : elements.entrySet()) {
                String id = entry.getKey();
                String cls = entry.getValue();

                // 如果id存在，则抛异常
                if (singletObjects.containsKey(id)) {
                    throw new IllegalAccessException("id 已存在");
                }

                // 反射实例化bean
                Class<?> aClass = Class.forName(cls);
                Object instance = aClass.newInstance();

                // 将实例化后的bean放到缓存中
                setBean(id, instance);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实例化bean对象并放入到缓存汇总
     *
     * @param id
     * @param obj
     */
    public void setBean(String id, Object obj) {
        singletObjects.put(id, obj);
    }
}
