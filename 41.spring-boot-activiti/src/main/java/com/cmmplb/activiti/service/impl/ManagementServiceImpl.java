package com.cmmplb.activiti.service.impl;

import com.cmmplb.activiti.service.ManagementService;
import com.cmmplb.activiti.vo.JobVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-20 15:59:43
 * @since jdk 1.8
 */

@Slf4j
@Service
@Transactional
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private org.activiti.engine.ManagementService managementService;

    @Override
    public PageResult<JobVO> getJobByPaged(Byte type, QueryPageBean bean) {
        List<Job> list = null;
        ArrayList<JobVO> res = new ArrayList<>();
        long count = 0;
        if (type == 1) {
            // 定时作业
            TimerJobQuery query = managementService.createTimerJobQuery();
            count = query.count();
            if (count == 0) {
                return new PageResult<>(count, new ArrayList<>());
            }
            list = query.orderByJobDuedate().desc().listPage(bean.getStart(), bean.getSize());
        } else if (type == 2) {
            // 异步作业
            JobQuery query = managementService.createJobQuery();
            count = query.count();
            if (count == 0) {
                return new PageResult<>(count, new ArrayList<>());
            }
            list = query.orderByJobDuedate().desc().listPage(bean.getStart(), bean.getSize());
        } else if (type == 3) {
            // 挂起作业
            SuspendedJobQuery query = managementService.createSuspendedJobQuery();
            count = query.count();
            if (count == 0) {
                return new PageResult<>(count, new ArrayList<>());
            }
            list = query.orderByJobDuedate().desc().listPage(bean.getStart(), bean.getSize());
        } else if (type == 4) {
            // 死亡作业
            DeadLetterJobQuery query = managementService.createDeadLetterJobQuery();
            count = query.count();
            if (count == 0) {
                return new PageResult<>(count, new ArrayList<>());
            }
            list = query.orderByJobDuedate().desc().listPage(bean.getStart(), bean.getSize());
        }
        if (!CollectionUtils.isEmpty(list)) {
            JobVO j;
            for (Job job : list) {
                j = new JobVO();
                j.setId(job.getId());
                j.setDueDate(job.getDuedate());
                j.setJobType(job.getJobType());
                j.setExceptionMessage(job.getExceptionMessage());
                j.setJobHandlerType(job.getJobHandlerType());
                j.setProcessDefinitionId(job.getProcessDefinitionId());
                j.setProcessInstanceId(job.getProcessInstanceId());
                j.setExecutionId(job.getExecutionId());
                res.add(j);
            }
        }
        return new PageResult<>(count, res);
    }
}
