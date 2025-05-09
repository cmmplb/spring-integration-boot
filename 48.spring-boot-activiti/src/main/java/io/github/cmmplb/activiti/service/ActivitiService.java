package io.github.cmmplb.activiti.service;

import io.github.cmmplb.activiti.vo.ProcessHistoricVO;
import io.github.cmmplb.activiti.vo.ProcessDefinitionVO;
import io.github.cmmplb.activiti.vo.TaskVO;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-12 11:27:29
 * @since jdk 1.8
 */
public interface ActivitiService {

    String deployProcessDefinition();

    List<ProcessDefinitionVO> getProcessDefinitionList();

    String startProcess(String processDefinitionId);

    List<TaskVO> getTaskList();

    String handleTask(String taskId);

    Boolean suspendProcessDefinition(String id);

    Boolean nativeApi();

    List<ProcessHistoricVO> getHistoryProcessList();

    Boolean deleteDeployment(String deploymentId);

    TaskVO queryTaskNode(String instanceId);
}
