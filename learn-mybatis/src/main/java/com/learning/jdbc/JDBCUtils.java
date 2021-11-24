package com.learning.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class JDBCUtils {

    private String url;

    private String name;

    private String pass;

    public JDBCUtils(String url, String name, String pass) {
        this.url = url;
        this.name = name;
        this.pass = pass;
    }

    public Map<String, Object> query(String sql, String... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, Object> result = null;
        try {

            ps = getConnection().prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setInt(i+1, Integer.parseInt(params[i]));
                }
            }

            rs = ps.executeQuery();

            if (null != rs) {
                result = new HashMap<>();
                while (rs.next()) {
                    result.put("c", rs.getInt("c"));
                    result.put("id", rs.getInt("id"));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * jdbc连接
     *
     * @return Connection
     */
    private Connection getConnection() throws Exception {
        // 加载mysql数据库驱动，这里是加载8.0版本
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取数据库连接
        return DriverManager.getConnection(url, name, pass);
    }
}
