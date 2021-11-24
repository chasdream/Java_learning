package com.learning.jdbc;

import com.learning.jdbc.bean.TBean;

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
public class JDBCTest {

    public static void main(String[] args) {

        JDBCUtils jdbcUtils = new JDBCUtils("jdbc:mysql://localhost:3306/mysql_learning", "root", "123456");

        Map<String, Object> query = jdbcUtils.query("select * from T where id = ?", "1");

        if (null != query) {
            try {
                TBean tBean = new TBean();
                tBean.setC(Integer.parseInt(query.get("c").toString()));
                tBean.setId(Integer.parseInt(query.get("id").toString()));
                System.out.println(tBean.toString());
            } catch (Exception e) {

            }
        }

    }
}
