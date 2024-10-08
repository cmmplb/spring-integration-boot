package com.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.plus.entity.Admin;
import com.cmmplb.mybatis.plus.entity.User;
import com.cmmplb.mybatis.plus.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

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
