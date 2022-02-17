package com.cmmplb.canal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmmplb.canal.dto.TestQueryDTO;
import com.cmmplb.canal.entity.Test;
import com.cmmplb.canal.vo.TestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Mapper
public interface TestMapper extends BaseMapper<Test> {

    Page<TestVO> selectByPaged(@Param("page") Page<TestVO> page, @Param("dto") TestQueryDTO dto);
}