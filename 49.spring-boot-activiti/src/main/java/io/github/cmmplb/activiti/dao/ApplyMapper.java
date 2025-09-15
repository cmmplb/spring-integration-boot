package io.github.cmmplb.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.activiti.dto.ApplyDTO;
import io.github.cmmplb.activiti.dto.ApplyStatisticsTimeDTO;
import io.github.cmmplb.activiti.entity.Apply;
import io.github.cmmplb.activiti.vo.ApplyDetailsVO;
import io.github.cmmplb.activiti.vo.ApplyStatisticsVO;
import io.github.cmmplb.activiti.vo.ApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

public interface ApplyMapper extends BaseMapper<Apply> {

    Page<ApplyVO> selectByPaged(@Param("page") Page<ApplyVO> page, @Param("dto") ApplyDTO dto);

    ApplyDetailsVO selectDetailsById(@Param("id") Long id);

    List<ApplyStatisticsTimeDTO> selectStatisticsList(@Param("type") Integer type);
}
