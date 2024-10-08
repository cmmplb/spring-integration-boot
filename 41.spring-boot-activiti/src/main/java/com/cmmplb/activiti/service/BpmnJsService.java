package com.cmmplb.activiti.service;

import com.cmmplb.activiti.dto.ModelBpmnDTO;
import com.cmmplb.activiti.vo.BpmnInfoVO;
import com.cmmplb.activiti.vo.BpmnProgressVO;
import io.github.cmmplb.core.result.Result;

/**
 * @author penglibo
 * @date 2023-12-13 16:15:32
 * @since jdk 1.8
 */
public interface BpmnJsService {

    BpmnInfoVO getBpmnInfo(String modelId);

    void export(String modelId);

    boolean saveDesign(String modelId, ModelBpmnDTO dto);

    String showFlowChart(String processDefinitionId);

    BpmnProgressVO showProgressChart(Long applyId);
}
