package io.github.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cmmplb.mybatis.plus.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select * from tb_admin limit #{limit}")
    Cursor<Admin> scan(@Param("limit") int limit);
}
