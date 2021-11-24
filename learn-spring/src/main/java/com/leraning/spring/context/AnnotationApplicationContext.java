package com.leraning.spring.context;

import com.leraning.spring.annotation.Component;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * <p>
 *  注解方式解析bean并实例化
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
public class AnnotationApplicationContext {

    /**
     * 匹配匿名内部类
     */
    private static final Pattern INNER_PATTERN = java.util.regex.Pattern.compile("\\$(\\d+).", java.util.regex.Pattern.CASE_INSENSITIVE);

    private static final String PACKAGE_NAME = "com.leraning.spring";

    private static final Map<String, Object> singletObjects = new ConcurrentHashMap<>();

    /**
     * 初始化bean
     */
    public void refresh() {
        try {
            // 扫描指定包下的.class文件
            Enumeration<URL> urls = findAllClasspathResources();

            Map<String, String> classMap = new HashMap<>();

            // 扫描项目的包，解析.class文件
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String file = URLDecoder.decode(url.getFile(), "utf-8");
                    File dir = new File(file);
                    if (dir.isDirectory()) {
                        parseClassFile(dir, PACKAGE_NAME, classMap);
                    } else {
                        throw new IllegalAccessException("file must be directory");
                    }
                }
            }

            // 实例化Bean
            for (Map.Entry<String, String> entry : classMap.entrySet()) {
                String key;
                Class<?> aClass = Class.forName(entry.getValue());
                Component annotation = aClass.getAnnotation(Component.class);

                // 如果注解存在，则进行实例化存储到singletObjects缓存中
                if (annotation != null) {
                    String valueKey = annotation.value();
                    if (!"".equals(valueKey)) {
                        key = valueKey;
                    } else {
                        Class<?>[] interfaces = aClass.getInterfaces();
                        String[] keys = interfaces[0].getName().split("\\.");
                        key = keys[keys.length - 1];
                    }
                    singletObjects.put(replaceFirst(key), aClass.newInstance());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母转换成小写
     *
     * @param key
     * @return
     */
    private String replaceFirst(String key) {
        if (Character.isLowerCase(key.charAt(0))) {
            return key;
        }
        return Character.toLowerCase(key.charAt(0)) + key.substring(1);
    }

    /**
     * 解析class文件
     *
     * @param dir
     * @param packageName
     * @param classMap
     */
    private void parseClassFile(File dir, String packageName, Map<String, String> classMap) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                parseClassFile(file, packageName, classMap);
            }
        } else if (dir.getName().endsWith(".class")) {
            String name = dir.getPath();
            name = name.substring(name.indexOf("classes") + 8).replace("/", ".");
            System.out.println("dir = " + dir + ", packageName = " + packageName + ", class = " + name);

            // 过滤掉匿名内部类
            if (INNER_PATTERN.matcher(name).find()) {
                System.out.println(", class = " + name + "是匿名内部类");
                return;
            }

            // 表示内部类
//            if (name.indexOf("$") > 0) {
//                System.out.println(", class = " + name + " inner class");
//            }

            // 如果不存在放入到classMap中
            String finalName = name;
            classMap.computeIfAbsent(name, s -> finalName.substring(0, finalName.length() - 6));
        }

    }

    /**
     * 解析jar包文件
     */
    private void parseJarFile(URL url, Map<String, String> classMap) throws IOException {
        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            String name = entry.getName();
            if (name.endsWith(".class")) {

                // 过滤掉匿名内部类
                if (INNER_PATTERN.matcher(name).find()) {
                    System.out.println(", class = " + name + "是匿名内部类");
                    return;
                }

                // 表示内部类
//                if (name.indexOf("$") > 0) {
//                    System.out.println(", class = " + name + " inner class");
//                }

                // 如果不存在放入到classMap中
                classMap.computeIfAbsent(name, s -> name.substring(0, name.length() - 6));
            }
        }
    }

    /**
     * 获取bean对象
     *
     * @param id
     * @return
     */
    public Object getBean(String id) {
        return singletObjects.get(id);
    }

    private Enumeration<URL> findAllClasspathResources() throws IOException {
        return this.getClass().getClassLoader().getResources(AnnotationApplicationContext.PACKAGE_NAME.replace(".", "/"));
    }
}
