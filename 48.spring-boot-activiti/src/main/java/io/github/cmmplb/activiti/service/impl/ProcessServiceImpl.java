package io.github.cmmplb.activiti.service.impl;

import io.github.cmmplb.activiti.dao.ExecutionMapper;
import io.github.cmmplb.activiti.entity.Execution;
import io.github.cmmplb.activiti.entity.User;
import io.github.cmmplb.activiti.image.ProcessDiagramCanvas;
import io.github.cmmplb.activiti.image.ProcessDiagramGeneratorImpl;
import io.github.cmmplb.activiti.service.ProcessService;
import io.github.cmmplb.activiti.service.UserService;
import io.github.cmmplb.activiti.util.ActivitiUtil;
import io.github.cmmplb.activiti.vo.ExecutionInstanceVO;
import io.github.cmmplb.activiti.vo.ProcessHistoricVO;
import io.github.cmmplb.activiti.vo.ProcessInstanceVO;
import io.github.cmmplb.activiti.vo.ProcessVariableVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.core.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author penglibo
 * @date 2023-11-17 15:31:12
 * @since jdk 1.8
 */

@Slf4j
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ExecutionMapper executionMapper;

    @Override
    public PageResult<ProcessInstanceVO> getInstanceByPaged(QueryPageBean pageBean) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        long count = query.count();
        if (count == 0) {
            return new PageResult<>(count, new ArrayList<>());
        }
        List<ProcessInstance> processList = query.orderByProcessDefinitionId().desc().listPage(pageBean.getStart(), pageBean.getSize());
        List<String> processInstanceIds = new ArrayList<>();
        Set<String> userIds = new HashSet<>();
        for (ProcessInstance processInstance : processList) {
            // processInstanceIds.add(processInstance.getId());
            // 表示流程实例的执行树的根的Id. 如果此执行是进程实例,则和id相同
            processInstanceIds.add(processInstance.getProcessInstanceId());
            userIds.add(processInstance.getStartUserId());
        }
        // 查看当前活动任务
        List<Task> taskList = taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
        for (Task task : taskList) {
            userIds.add(task.getAssignee());
        }
        Map<Long, User> userMap = null;
        if (!CollectionUtils.isEmpty(userIds)) {
            List<User> userList = userService.getListByIds(userIds);
            userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        }

        List<ProcessInstanceVO> list = new ArrayList<>();
        ProcessInstanceVO vo;
        for (ProcessInstance processInstance : processList) {
            vo = new ProcessInstanceVO();
            vo.setId(processInstance.getProcessInstanceId());
            vo.setName(processInstance.getName());
            vo.setExecutionId(processInstance.getId());
            vo.setBusinessKey(processInstance.getBusinessKey());
            vo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
            vo.setStartTime(processInstance.getStartTime());
            vo.setStartUserId(processInstance.getStartUserId());
            if (!CollectionUtils.isEmpty(userMap)) {
                // 流程启动人姓名
                User user = userMap.get(Long.parseLong(processInstance.getStartUserId()));
                if (null != user) {
                    vo.setStartUserName(user.getName());
                }
            }
            vo.setIsSuspended(processInstance.isSuspended());
            vo.setEnded(processInstance.isEnded());
            if (!CollectionUtils.isEmpty(taskList)) {
                StringBuilder taskName = new StringBuilder();
                StringBuilder assignee = new StringBuilder();
                for (Task task : taskList) {
                    if (task.getProcessInstanceId().equals(processInstance.getProcessInstanceId())) {
                        taskName.append(task.getName()).append(",");
                    }
                    if (!CollectionUtils.isEmpty(userMap)) {
                        // 负责人姓名
                        User user = userMap.get(Long.parseLong(task.getAssignee()));
                        if (null != user) {
                            assignee.append(user.getName()).append(",");
                        }
                    }
                }
                taskName = new StringBuilder(taskName.substring(0, taskName.length() - 1));
                assignee = new StringBuilder(assignee.substring(0, assignee.length() - 1));
                vo.setTaskName(taskName.toString());
                vo.setAssigneeName(assignee.toString());
            }
            list.add(vo);
        }
        return new PageResult<>(count, list);
    }

    @Override
    public void showProcessInstanceProgressChart(String processInstanceId) {
        try {

            // 获取流程中已经执行的节点, 按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceStartTime().asc().list();

            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                    .singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            // highLightedActivities（需要高亮的执行流程节点集合的获取）以及
            // highLightedFlows（需要高亮流程连接线集合的获取）
            // 高亮流程已发生流转的线id集合
            List<String> highLightedFlowIds = ActivitiUtil.getHighLightedFlows(bpmnModel, historicActivityInstances);

            // 高亮已经执行流程节点ID集合
            List<String> highLightedActivitiIds = historicActivityInstances.stream().map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());

            // 当前激活的流程节点
            Set<String> activityIds = runtimeService.createExecutionQuery().processInstanceId(historicProcessInstance.getId()).list()
                    .stream().map(org.activiti.engine.runtime.Execution::getActivityId).collect(Collectors.toSet());
            // activiti7移除了静态方法创建, 需要DefaultProcessDiagramGenerator实例
            // ProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
            // 由于是创建的新实例, 这里的DiagramGenerator就不用注入到配置类里面了, 当然ActivitiConfiguration配置类也移除了set的方法. 
            ProcessDiagramGeneratorImpl diagramGenerator = new ProcessDiagramGeneratorImpl();
            // 使用默认配置获得流程图表生成器, 并生成追踪图片字符流
            InputStream is = diagramGenerator.generateDiagram(bpmnModel,
                    highLightedActivitiIds,
                    highLightedFlowIds,
                    "宋体",
                    "微软雅黑",
                    "黑体",
                    new Color[]{ProcessDiagramCanvas.COLOR_NORMAL, ProcessDiagramCanvas.COLOR_CURRENT},
                    activityIds
            );

            HttpServletResponse response = ServletUtil.getResponse();

            // 响应svg到客户端
            // response.setContentType("image/svg+xml");
            // IOUtils.copy(is, response.getOutputStream());

            // 转换svg为png响应
            response.setContentType("image/png");
            new PNGTranscoder().transcode(new TranscoderInput(is), new TranscoderOutput(response.getOutputStream()));
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @Override
    public boolean suspendProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return true;
    }

    @Override
    public boolean activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return true;
    }

    @Override
    public List<ExecutionInstanceVO> getExecutionInstanceList() {
        List<Execution> executionList = executionMapper.selectExecutionProcessByPaged();
        List<ExecutionInstanceVO> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(executionList)) {
            Set<String> procInstIds = executionList.stream().map(Execution::getProcInstId).collect(Collectors.toSet());
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(procInstIds).list();

            List<Task> taskList = taskService.createTaskQuery().processInstanceIdIn(new ArrayList<>(procInstIds)).list();
            ExecutionInstanceVO info;
            for (Execution execution : executionList) {
                info = new ExecutionInstanceVO();
                info.setExecutionId(execution.getId());
                // 暂停状态,挂起状态:1-激活;2-挂起;
                info.setSuspended(execution.getSuspensionState() == 1 ? "否" : "是");
                // 激活状态,是否存活
                info.setActive(execution.getIsActive() == 0 ? "否" : "是");
                if (execution.getActId() != null) {
                    for (ProcessInstance processInstance : processInstanceList) {
                        if (execution.getProcInstId().equals(processInstance.getId())) {
                            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
                            Map<String, FlowElement> nodes = bpmnModel.getMainProcess().getFlowElementMap();
                            info.setTaskName(nodes.get(execution.getActId()).getName());
                            info.setProcessDefinitionName(processInstance.getProcessDefinitionName());
                            break;
                        }
                    }
                } else {
                    for (ProcessInstance processInstance : processInstanceList) {
                        if (execution.getProcInstId().equals(processInstance.getId())) {
                            info.setStartTime(processInstance.getStartTime());
                            info.setStartUserId(processInstance.getStartUserId());
                            info.setProcessDefinitionName(processInstance.getProcessDefinitionName());
                            if (!CollectionUtils.isEmpty(taskList)) {
                                StringBuilder taskName = new StringBuilder();
                                for (Task t : taskList) {
                                    taskName.append(t.getName()).append(",");
                                }
                                taskName = new StringBuilder(taskName.substring(0, taskName.length() - 1));
                                info.setTaskName(taskName.toString());
                            }
                            break;
                        }
                    }
                }
                info.setStartTime(execution.getStartTime());
                info.setExecutionId(execution.getId());
                info.setParentExecutionId(null == execution.getParentId() ? "0" : execution.getParentId());
                res.add(info);
            }
        }

        // 转换树形
        return getChildren("0", res);
    }

    @Override
    public PageResult<ProcessInstanceVO> getAllInstanceByPaged(QueryPageBean bean) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        long count = query.count();
        if (count == 0) {
            return new PageResult<>(count, new ArrayList<>());
        }
        List<HistoricProcessInstance> processList = query.orderByProcessInstanceStartTime().desc().listPage(bean.getStart(), bean.getSize());
        List<String> processInstanceIds = new ArrayList<>();
        Set<String> userIds = new HashSet<>();
        for (HistoricProcessInstance processInstance : processList) {
            processInstanceIds.add(processInstance.getId());
            userIds.add(processInstance.getStartUserId());
        }
        // 查看当前活动任务
        List<Task> taskList = taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
        for (Task task : taskList) {
            userIds.add(task.getAssignee());
        }
        Map<Long, User> userMap = null;
        if (!CollectionUtils.isEmpty(userIds)) {
            List<User> userList = userService.getListByIds(userIds);
            userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        }

        List<ProcessInstanceVO> res = new ArrayList<>();
        for (HistoricProcessInstance instance : processList) {
            ProcessInstanceVO vo = new ProcessInstanceVO();
            vo.setId(instance.getId());
            vo.setBusinessKey(instance.getBusinessKey());
            vo.setName(instance.getName());
            vo.setProcessDefinitionName(instance.getProcessDefinitionName());
            vo.setStartTime(instance.getStartTime());
            vo.setEndTime(instance.getEndTime());
            vo.setStartUserId(instance.getStartUserId());
            vo.setStartUserName(null == userMap ? "" : userMap.get(Long.parseLong(instance.getStartUserId())).getName());
            if (instance.getEndTime() == null) {
                vo.setEnded(false);
                if (!CollectionUtils.isEmpty(taskList)) {
                    StringBuilder taskName = new StringBuilder();
                    StringBuilder assignee = new StringBuilder();
                    for (Task task : taskList) {
                        if (task.getProcessInstanceId().equals(instance.getId())) {
                            taskName.append(task.getName()).append(",");
                        }
                        if (!CollectionUtils.isEmpty(userMap)) {
                            // 负责人姓名
                            User user = userMap.get(Long.parseLong(task.getAssignee()));
                            if (null != user) {
                                assignee.append(user.getName()).append(",");
                            }
                        }
                    }
                    taskName = new StringBuilder(taskName.substring(0, taskName.length() - 1));
                    assignee = new StringBuilder(assignee.substring(0, assignee.length() - 1));
                    vo.setTaskName(taskName.toString());
                    vo.setAssigneeName(assignee.toString());
                }
            } else {
                vo.setEnded(true);
            }
            res.add(vo);
        }
        return new PageResult<>(count, res);
    }

    @SuppressWarnings({"deprecation"})
    @Override
    public PageResult<ProcessHistoricVO> getProcessHistory(String processInstanceId, QueryPageBean bean) {
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask");
        long count = query.count();
        if (count == 0) {
            return new PageResult<>(count, new ArrayList<>());
        }
        // 活动类型,如startEvent、userTask
        List<HistoricActivityInstance> history = query.orderByHistoricActivityInstanceStartTime().asc().listPage(bean.getStart(), bean.getSize());
        Set<String> userIds = history.stream().map(HistoricActivityInstance::getAssignee).collect(Collectors.toSet());
        Map<Long, User> userMap = null;
        if (!CollectionUtils.isEmpty(userIds)) {
            List<User> userList = userService.getListByIds(userIds);
            userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        }
        List<ProcessHistoricVO> res = new ArrayList<>();
        ProcessHistoricVO info;
        for (HistoricActivityInstance instance : history) {
            info = new ProcessHistoricVO();
            info.setProcessInstanceId(instance.getProcessInstanceId());
            info.setStartTime(instance.getStartTime());
            info.setEndTime(instance.getEndTime());
            if (!CollectionUtils.isEmpty(userMap)) {
                info.setAssigneeName(userMap.get(Long.parseLong(instance.getAssignee())).getName());
            }
            info.setTaskName(instance.getActivityName());
            List<Comment> comments = taskService.getTaskComments(instance.getTaskId());
            if (comments.size() > 0) {
                info.setComment(comments.get(0).getFullMessage());
            }
            res.add(info);
        }
        return new PageResult<>(count, res);
    }

    @Override
    public PageResult<ProcessVariableVO> getProcessVariable(String processInstanceId, QueryPageBean bean) {
        long count = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).count();
        if (count == 0) {
            return new PageResult<>(count, new ArrayList<>());
        }
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).listPage(bean.getStart(), bean.getSize());
        List<ProcessVariableVO> res = new ArrayList<>();
        ProcessVariableVO vo;
        for (HistoricVariableInstance instance : list) {
            vo = new ProcessVariableVO();
            vo.setId(instance.getId());
            vo.setVariableName(instance.getVariableName());
            vo.setVariableTypeName(instance.getVariableTypeName());
            vo.setValue(instance.getValue().toString());
            vo.setCreateTime(instance.getCreateTime());
            vo.setLastUpdatedTime(instance.getLastUpdatedTime());
            res.add(vo);
        }
        return new PageResult<>(count, res);
    }

    public static List<ExecutionInstanceVO> getChildren(String parentId, List<ExecutionInstanceVO> treeList) {
        return treeList.stream()
                // 筛选父节点
                .filter(vo -> vo.getParentExecutionId().equals(parentId))
                // 递归设置子节点
                .peek(item -> item.setChildren(getChildren(item.getExecutionId(), treeList)))
                .collect(Collectors.toList());
    }
}
