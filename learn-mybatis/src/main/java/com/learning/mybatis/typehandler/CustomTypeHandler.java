package com.learning.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 * <p>
 * 自定义类型处理器，实现org.apache.ibatis.type.TypeHandler接口
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/3/1
 */
public class CustomTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            // 设置参数的时候对参数进行加密
            String encode = Base64.getEncoder().encodeToString(((String) parameter).getBytes());
            ps.setString(i, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        // 获取结果集对加密数据进行解密
        String result = rs.getString(columnName);
        if (result != null && !result.equals("")) {
            try {
                return Base64.getDecoder().decode(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        // 获取结果集对加密数据进行解密
        String result = rs.getString(columnIndex);
        if (result != null && !result.equals("")) {
            try {
                return Base64.getDecoder().decode(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 获取结果集对加密数据进行解密
        String result = cs.getString(columnIndex);
        if (result != null && !result.equals("")) {
            try {
                return Base64.getDecoder().decode(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
