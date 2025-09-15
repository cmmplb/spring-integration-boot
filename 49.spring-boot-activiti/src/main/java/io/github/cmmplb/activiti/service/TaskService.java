package io.github.cmmplb.activiti.service;

import io.github.cmmplb.activiti.dto.HandleTaskDTO;
import io.github.cmmplb.activiti.dto.TaskQueryDTO;
import io.github.cmmplb.activiti.vo.CompletedTaskVO;
import io.github.cmmplb.activiti.vo.IncompleteTaskVO;
import io.github.cmmplb.core.beans.PageResult;

/**
 * @author penglibo
 * @date 2023-11-16 10:42:05
 * @since jdk 1.8
 */
public interface TaskService {

    PageResult<IncompleteTaskVO> getByPaged(TaskQueryDTO dto);

    PageResult<CompletedTaskVO> getCompletedByPaged(TaskQueryDTO dto);

    boolean handleTask(HandleTaskDTO dto);

    boolean entrustTask(String taskId, String userId);
}
