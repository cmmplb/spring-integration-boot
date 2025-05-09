package io.github.cmmplb.activiti.service;

import io.github.cmmplb.activiti.dto.SuspendActivateProcessDefinitionDTO;
import io.github.cmmplb.activiti.vo.ProcessDefinitionVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author penglibo
 * @date 2023-11-15 10:34:23
 * @since jdk 1.8
 */
public interface DeployService {

    PageResult<ProcessDefinitionVO> getByPaged(QueryPageBean queryPageBean);

    boolean upload(MultipartFile[] files);

    boolean removeById(String deploymentId);

    void showProcessDefinition(String deploymentId, String resource);

    void showProcessChart(String processDefinitionId);

    boolean exchangeProcessToModel(String processDefinitionId);

    boolean suspendProcessDefinition(String processDefinitionId, SuspendActivateProcessDefinitionDTO dto);

    boolean activateProcessDefinition(String processDefinitionId, SuspendActivateProcessDefinitionDTO dto);
}
