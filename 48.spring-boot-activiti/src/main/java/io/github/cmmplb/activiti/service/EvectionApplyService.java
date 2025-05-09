package io.github.cmmplb.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.activiti.dto.EvectionApplyDTO;
import io.github.cmmplb.activiti.dto.LeaveApplyDTO;
import io.github.cmmplb.activiti.entity.EvectionApply;
import io.github.cmmplb.activiti.entity.LeaveApply;
import io.github.cmmplb.activiti.vo.EvectionApplyDetailsVO;

/**
 * @author penglibo
 * @date 2023-11-27 09:17:28
 * @since jdk 1.8
 */
public interface EvectionApplyService extends IService<EvectionApply> {

    boolean save(EvectionApplyDTO dto);

    EvectionApplyDetailsVO getDetailsById(Long id);
}
