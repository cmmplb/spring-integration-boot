package io.github.cmmplb.activiti.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.activiti.dao.LeaveApplyDateMapper;
import io.github.cmmplb.activiti.entity.LeaveApplyDate;
import io.github.cmmplb.activiti.service.LeaveApplyDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author penglibo
 * @date 2023-11-23 16:46:26
 * @since jdk 1.8
 */

@Slf4j
@Service
@Transactional
public class LeaveApplyDateServiceImpl extends ServiceImpl<LeaveApplyDateMapper, LeaveApplyDate> implements LeaveApplyDateService {
}
