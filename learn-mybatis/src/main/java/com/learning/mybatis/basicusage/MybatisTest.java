package com.learning.mybatis.basicusage;

import com.learning.mybatis.basicusage.entity.TEntity;
import com.learning.mybatis.basicusage.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class MybatisTest {

    public static void main(String[] args) throws IOException {

        // ----- 第一阶段：初始化配置阶段------
        // 第一步：读取mybatis-config.xml配置文
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        // 第二步：构建SessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // ----- 第二阶段：代理阶段--------
        // 第三步：打开sqlSession
        SqlSession session = sqlSessionFactory.openSession();

        // 第四步：获取mapper接口对象
        UserMapper userMapper = session.getMapper(UserMapper.class);

        // ----- 第三阶段：数据处理阶段
        // 第五步：调用Mapper接口对象的方法操作数据库
        List<TEntity> tBeans = userMapper.selectById(1);

        // 第六步：业务逻辑处理
        System.out.println(tBeans);
    }

}
