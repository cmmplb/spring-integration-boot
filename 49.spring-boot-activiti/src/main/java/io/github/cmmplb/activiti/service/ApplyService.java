package io.github.cmmplb.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.activiti.dto.ApplyDTO;
import io.github.cmmplb.activiti.dto.ApplyStatisticsTimeDTO;
import io.github.cmmplb.activiti.entity.Apply;
import io.github.cmmplb.activiti.entity.LeaveApplyDate;
import io.github.cmmplb.activiti.vo.ApplyDetailsVO;
import io.github.cmmplb.activiti.vo.ApplyStatisticsVO;
import io.github.cmmplb.activiti.vo.ApplyVO;
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
