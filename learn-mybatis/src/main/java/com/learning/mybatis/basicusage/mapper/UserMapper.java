package com.learning.mybatis.basicusage.mapper;

import com.learning.mybatis.basicusage.entity.TEntity;
import org.apache.ibatis.annotations.Param;

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
public interface UserMapper {

    List<TEntity> selectById(@Param("id") int id);
}
