package com.learning.rpc.common.serialize.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.learning.rpc.common.serialize.Serialization;

import java.text.SimpleDateFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class JSONSerialization implements Serialization {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps对象
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        // 忽略空Bean转json的错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 所有日期统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略 在json字符串中存在, 但是在java对象中不存在对应属性的情况, 防止出错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }
    @Override
    public byte[] serialize(Object output) throws Exception {
        return objectMapper.writeValueAsBytes(output);
    }

    @Override
    public Object deserialize(byte[] input, Class clazz) throws Exception {
        return objectMapper.readValue(input, clazz);
    }
}
