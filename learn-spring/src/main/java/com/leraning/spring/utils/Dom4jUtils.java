package com.leraning.spring.utils;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 *  解析xml文件
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
public final class Dom4jUtils {

    private Dom4jUtils() {}

    public static Map<String, String> readerXml(String xmlPath) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(Dom4jUtils.class.getClassLoader().getResourceAsStream(xmlPath));
        Element root = document.getRootElement();// 获取根节点
        // 遍历当前节点的所有属性节点
//        List<Attribute> attributes = root.attributes();
//        for (Attribute attribute : attributes) {
//            System.out.println(attribute.getText());
//        }

        // 递归获取子元素
        Map<String, String> eleMap = new HashMap<>();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element node = iterator.next();
            String id = node.attributeValue("id");
            String clazz = node.attributeValue("class");
            System.out.println(id + " : " + clazz);
            eleMap.put(id, clazz);
        }

        return eleMap;
    }
}
