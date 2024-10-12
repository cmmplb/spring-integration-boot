package com.cmmplb.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.activiti.dto.ApplyDTO;
import com.cmmplb.activiti.dto.ApplyStatisticsTimeDTO;
import com.cmmplb.activiti.entity.Apply;
import com.cmmplb.activiti.entity.LeaveApplyDate;
import com.cmmplb.activiti.vo.ApplyDetailsVO;
import com.cmmplb.activiti.vo.ApplyStatisticsVO;
import com.cmmplb.activiti.vo.ApplyVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-15 11:04:14
 * @since jdk 1.8
 */
public interface ApplyService extends IService<Apply> {

    PageResult<ApplyVO> getByPaged(ApplyDTO dto);

    boolean cancelApply(Long id);

    boolean deleteById(Long id);

    ApplyDetailsVO getApplyDetailsById(Long id);

    void showProgressChart(Long id);

    List<ApplyStatisticsTimeDTO> getStatisticsList(Integer type);
}
