package com.cmmplb.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.activiti.dto.LeaveApplyDTO;
import com.cmmplb.activiti.entity.LeaveApply;
import com.cmmplb.activiti.vo.LeaveApplyDetailsVO;
import com.cmmplb.activiti.vo.LeaveApplyVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;

/**
 * @author penglibo
 * @date 2023-11-15 11:04:14
 * @since jdk 1.8
 */
public interface LeaveApplyService extends IService<LeaveApply> {

    boolean save(LeaveApplyDTO dto);

    LeaveApplyDetailsVO getDetailsById(Long id);
}
