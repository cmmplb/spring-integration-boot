package com.cmmplb.activiti.service;

import com.cmmplb.activiti.vo.ExecutionInstanceVO;
import com.cmmplb.activiti.vo.ProcessHistoricVO;
import com.cmmplb.activiti.vo.ProcessInstanceVO;
import com.cmmplb.activiti.vo.ProcessVariableVO;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-17 15:31:04
 * @since jdk 1.8
 */
public interface ProcessService {

    PageResult<ProcessInstanceVO> getInstanceByPaged(QueryPageBean queryPageBean);

    void showProcessInstanceProgressChart(String processInstanceId);

    boolean suspendProcessInstance(String processInstanceId);

    boolean activateProcessInstance(String processInstanceId);

    List<ExecutionInstanceVO> getExecutionInstanceList();

    PageResult<ProcessInstanceVO> getAllInstanceByPaged(QueryPageBean queryPageBean);

    PageResult<ProcessHistoricVO> getProcessHistory(String processInstanceId, QueryPageBean queryPageBean);

    PageResult<ProcessVariableVO> getProcessVariable(String processInstanceId, QueryPageBean queryPageBean);
}
