package com.learning.dbUtils;

import com.learning.jdbc.bean.TBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *  commons-dbutils jdbc封装工具
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class QueryRunTest {

    public static void main(String[] args) throws SQLException {
//        insert();
        query();
    }

    private static void insert() throws SQLException {
        QueryRunner runner = new QueryRunner(DBUtils.getDataSource());
        Object[] params = {2, 2};
        runner.update("insert into T(id, c) value (?, ?)", params);
    }

    private static void query() throws SQLException {
        QueryRunner runner = new QueryRunner(DBUtils.getDataSource());
        Object query = runner.query("select * from T where id = 2", new ResultSetHandler<Object>() {
            @Override
            public Object handle(ResultSet resultSet) throws SQLException {
                TBean tBean = new TBean();
                while (resultSet.next()) {
                    tBean.setId(resultSet.getInt("id"));
                    tBean.setC(resultSet.getInt("c"));
                }
                return tBean;
            }
        });
        System.out.println(query);
    }
}
