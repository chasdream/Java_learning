package com.learning.rpc.common.tools;

import java.net.URI;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class URIUtils {

    /**
     * 解析uri的参数名称
     * @param uri
     * @param paramName
     * @return
     */
    public static String getParam(URI uri, String paramName) {
        for (String param : uri.getQuery().split("&")) {
            if (param.startsWith(paramName + "=")) {
                return param.replace(paramName + "=", "");
            }
        }
        return null;
    }

    public static String getService(URI uri) {
        return uri.getPath().replace("/", "");
    }
}
