package com.cmmplb.activiti.service.impl;

import com.cmmplb.activiti.service.ActivitiService;
import com.cmmplb.activiti.vo.ProcessHistoricVO;
import com.cmmplb.activiti.vo.ProcessDefinitionVO;
import com.cmmplb.activiti.vo.TaskVO;
import com.cmmplb.core.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-10-12 11:27:38
 * @since jdk 1.8
 */

@Slf4j
@Service
@Transactional
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Override
    public String deployProcessDefinition() {
        // ===============单个文件部署方式===============
        // 流程的部署, 是把我们所创建的流程定义文件与数据库的表关联起来
        Deployment deploy = this.repositoryService.createDeployment()
                .name("请假申请")
                .category("日常") // 设置流程类型
                .key("leave-apply-key")
                .addClasspathResource("processes/leave-apply.bpmn20.xml")
                .deploy();
        // ===============压缩包部署方式, 定义zip输入流===============
        // InputStream inputStream = this
        //         .getClass()
        //         .getClassLoader()
        //         .getResourceAsStream(
        //                 "bpmn/leave-apply-key.zip");
        // ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // Deployment deploy = repositoryService.createDeployment()
        //         .addZipInputStream(zipInputStream)
        //         .deploy();

        log.info("同时会在act_ge_bytearray ,act_re_deployment ,act_re_procdef这3个表中插入相关信息");
        // act_re_deployment和act_re_procdef一对多关系, 一次部署在流程部署表生成一条记录, 
        // 但一次部署可以部署多个流程定义, 每个流程定义在流程定义表生成一条记录. 
        // 每一个流程定义在act_ge_bytearray会存在两个资源记录, bpmn和png. 

        // 建议：一次部署一个流程, 这样部署表和流程定义表是一对一有关系, 方便读取流程部署及流程定义信息. 
        return "部署流程定义成功：" + deploy.getName();
    }

    @Override
    public List<ProcessDefinitionVO> getProcessDefinitionList() {
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        // key查询
        // List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey(key).list();
        List<ProcessDefinition> definitionList = definitionQuery.list();
        // 提取所有的流程名称
        List<ProcessDefinitionVO> processList = new ArrayList<>();
        ProcessDefinitionVO vo;
        for (ProcessDefinition definition : definitionList) {
            vo = new ProcessDefinitionVO();
            vo.setId(definition.getId());
            vo.setName(definition.getName());
            vo.setCategory(definition.getCategory());
            vo.setKey(definition.getKey());
            vo.setDescription(definition.getDescription());
            vo.setVersion(definition.getVersion());
            vo.setResourceName(definition.getResourceName());
            vo.setDeploymentId(definition.getDeploymentId());
            processList.add(vo);
        }
        return processList;
    }

    @Override
    public String startProcess(String processDefinitionId) {
        // 启动 key 标识的流程定义, 并指定 流程定义中的参数：assignee0和assignee1、2
        Map<String, Object> map = new HashMap<>();
        map.put("assignee0", "张三");
        map.put("assignee1", "李四");
        map.put("assignee2", "王五");

        // 根据流程定义ID查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

        // 获取流程定义的Key
        String processDefinitionKey = processDefinition.getKey();

        // 定义businessKey  businessKey一般为流程实例key与实际业务数据的结合
        // 假设一个业务在数据库中的id
        int businessId = RandomUtil.getNum(1, 1000000);
        String businessKey = processDefinitionKey + ":" + businessId;

        // 设置启动流程的人,这个会在act_ru_execution当前执行表中START_USER_ID_字段显示,这个表可以看做是比Task记录的节点更详细的表,会记录所有的节点, 而且它主要用于子流程
        Authentication.setAuthenticatedUserId("老八");

        // 通过流程定义ID启动, 这个ID就是act_re_procdef的主键ID 例如Leave:1:4
        // ProcessInstance processInstance = runtimeService.startProcessInstanceById(id);
        // 通过流程定义的Key启动, 这个Key是在我们画流程图的时候输入的ID
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, map);

        // ru运行时、hi历史数据
        // act_ru_task任务表, 该表中新增了一条记录, 为创建申请, 负责人ASSIGNEE_为张三
        // act_ru_execution当前执行表, 新增两条记录, 为子流程
        // act_hi_taskinst历史任务流程实例表,保存的内容大体跟ru_task一致
        // act_hi_procinst历史流程实例表, 用于查询流程的详细信息, 比如启动时间, 启动人, 结束时间等等
        // act_hi_actinst流程活动实例表 这个表从存储的内容来说和act_ru_execution比较像, 它会把流程所有已经流转过的节点都记录下来
        return "流程实例的内容：" + processInstance.getProcessDefinitionName();
    }

    @Override
    public List<TaskVO> getTaskList() {
        // 获取任务列表
        TaskQuery query = taskService.createTaskQuery().orderByTaskCreateTime().asc();

        // 这里根据业务拼接条件
        // query.taskAssignee("张三"); //只查询该任务负责人的任务
        // 流程定义key为leave-apply-key的任务
        // query.processDefinitionKey("leave-apply-key");
        List<Task> list = query.list();
        List<TaskVO> tasks = new ArrayList<>();
        TaskVO vo;
        for (Task task : list) {
            vo = new TaskVO();
            vo.setId(task.getId());
            vo.setName(task.getName());
            vo.setProcessInstanceId(task.getProcessInstanceId());
            vo.setProcessDefinitionId(task.getProcessDefinitionId());
            tasks.add(vo);
        }
        return tasks;
    }

    @Override
    public String handleTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        // 如果当前任务没有指派人, 需要先使用 claim() 方法领取任务
        // taskService.claim(id, "李四");

        // 如果设置为null, 归还组任务,该 任务没有负责人
        // taskService.setAssignee(taskId, null);

        // 将此任务交给其它候选人办理该 任务
        // taskService.setAssignee(taskId, "李四");

        // 添加评论
        taskService.addComment(taskId, task.getProcessInstanceId(), "同意");
        // 查看评论表act_hi_comment,查看act_ru_task表, 可以看到任务流转：
        // 如果有指派人, 直接完成任务
        taskService.complete(taskId);

        String processInstanceId = task.getProcessInstanceId();

        // 这两个可以用来判断流程是否结束,看看后续是不是可以整合业务
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (null == processInstance) {
            log.info("processInstance流程结束");
        }
        Task taskResult = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (null == taskResult) {
            log.info("taskResult流程结束");
        }

        // 完成最后一个任务的审批后, 会更新历史流程实例的完成时间act_hi_procinst, DURATION_字段
        // ru相关表数据都会清空, 流程结束
        return task.getName();
    }

    @Override
    public Boolean suspendProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        if (processDefinition.isSuspended()) {
            log.info("激活");
            repositoryService.activateProcessDefinitionById(processDefinitionId);
        } else {
            log.info("暂停");
            //  如果是暂停, 可以执行激活操作 ,参数1 ：流程定义id , 参数2：是否激活, 参数3：激活时间
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        }
        try {
            runtimeService.startProcessInstanceById(processDefinitionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processDefinition.isSuspended();
    }

    @Override
    public Boolean nativeApi() {
        log.info("===========================Api查询===========================");
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("张三")
                .processVariableValueEquals("name", "张三")
                .orderByDueDateNullsFirst().asc()
                .list();
        for (Task task : tasks) {
            log.info("任务id：{}", task.getId());
            log.info("任务名称：{}", task.getName());
        }
        System.out.println();
        log.info("===========================原生任务查询===========================");
        NativeTaskQuery nativeTaskQuery = taskService.createNativeTaskQuery()
                .sql("select * from act_ru_task art where art.NAME_ = #{taskName}")
                .parameter("taskName", "创建申请");

        // 执行查询并获取结果
        tasks = nativeTaskQuery.list();

        // 处理查询结果
        for (Task task : tasks) {
            log.info("任务id：{}", task.getId());
            log.info("任务名称：{}", task.getName());
        }
        return true;
    }

    @Override
    public List<ProcessHistoricVO> getHistoryProcessList() {
        // 在 API 中, 可以查询所有 5 个历史记录实体：
        // historyService.createHistoricTaskInstanceQuery();
        // historyService.createHistoricActivityInstanceQuery();
        // historyService.createHistoricDetailQuery();
        // historyService.createHistoricProcessInstanceQuery();
        // historyService.createHistoricVariableInstanceQuery();

        // 1.历史过程实例查询
        HistoricProcessInstanceQuery hisProcInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        hisProcInstanceQuery.processDefinitionKey("leave-apply-key").orderByProcessInstanceDuration().desc().listPage(0, 10);

        // 2.历史变量实例查询
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId("112995b5-6ccc-11ee-b137-b2d9c0e0bcff").orderByVariableName().desc().list();

        // 3. 历史活动实例查询
        List<HistoricActivityInstance> userTask = historyService.createHistoricActivityInstanceQuery()
                .activityType("userTask").processDefinitionId("leave:1:10004").finished()
                .orderByHistoricActivityInstanceEndTime().desc().listPage(0, 1);

        // 4. 历史细节查询
        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery()
                .variableUpdates().processInstanceId("60002").orderByVariableName().asc().list();

        // 5. 历史任务实例查询
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .finished().orderByHistoricTaskInstanceDuration().desc().listPage(0, 10);

        // 6.查询未结束的流程
        // List<HistoricProcessInstance> unfinishedList = hisProcInstanceQuery.unfinished().list();

        // 不加查询条件
        List<HistoricProcessInstance> processInstances = hisProcInstanceQuery.list();

        List<ProcessHistoricVO> list = new ArrayList<>();
        ProcessHistoricVO vo;
        for (HistoricProcessInstance instance : processInstances) {
            vo = new ProcessHistoricVO();
            vo.setId(instance.getId());
            vo.setBusinessKey(instance.getBusinessKey());
            vo.setProcessDefinitionId(instance.getProcessDefinitionId());
            vo.setProcessDefinitionName(instance.getProcessDefinitionName());
            vo.setProcessDefinitionKey(instance.getProcessDefinitionKey());
            vo.setProcessDefinitionVersion(instance.getProcessDefinitionVersion());
            vo.setDeploymentId(instance.getDeploymentId());
            vo.setStartTime(instance.getStartTime());
            vo.setEndTime(instance.getEndTime());
            vo.setDurationInMillis(instance.getDurationInMillis());
            vo.setStartUserId(instance.getStartUserId());
            vo.setName(instance.getName());
            vo.setDescription(instance.getDescription());
            list.add(vo);
        }
        // 根据流程定义名称查询
        // List<HistoricProcessInstance> myProcessInstanceList = hisProcInstanceQuery.processDefinitionName("我的流程").list();
        // 根据流程实例ID查询,如果数据库中只有一条流程那么也是可以将list()改为singleResult()的
        // HistoricProcessInstance processInstanceList = hisProcInstanceQuery.processInstanceId("2501").singleResult();

        return list;
    }

    @Override
    public Boolean deleteDeployment(String deploymentId) {
        /**
         * 说明：
         * 1)使用repositoryService删除流程定义, 历史表信息不会被删除
         * 2)如果该流程定义下没有正在运行的流程, 则可以用普通删除. 
         * 如果该流程定义下存在已经运行的流程, 使用普通删除报错, 可用级联删除方法将流程及相关记录全部删除. 
         * 先删除没有完成流程节点, 最后就可以完全删除流程定义信息
         * 项目开发中级联删除操作一般只开放给超级管理员使用.
         */
        // 删除流程定义, 如果该流程定义已有流程实例启动则删除时出错
        repositoryService.deleteDeployment(deploymentId);
        // 设置true级联删除流程定义, 即使该流程有流程实例启动也可以删除, 设置为false非级别删除方式, 如果流程
        // repositoryService.deleteDeployment(deploymentId, true);
        return true;
    }

    @Override
    public TaskVO queryTaskNode(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance == null) {
            log.info("执行完毕");
        } else {
            log.info("正在执行");
        }
        // 获取任务列表
        TaskQuery query = taskService.createTaskQuery().orderByTaskCreateTime().asc();
        query.processInstanceId(instanceId);
        Task task = query.singleResult();
        if (null == task) {
            return null;
        }
        TaskVO vo = new TaskVO();
        vo.setId(task.getId());
        vo.setName(task.getName());
        vo.setProcessDefinitionId(task.getProcessDefinitionId());
        return vo;
    }
}
