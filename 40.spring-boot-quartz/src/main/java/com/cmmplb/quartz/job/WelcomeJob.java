package com.cmmplb.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author penglibo
 * @date 2023-02-09 14:43:36
 * @since jdk 1.8
 * 可以通过实现 Job 接口来定义任务，也可以通过继承 QuartzJobBean 这个抽象类来定义任务，
 * 其实 QuartzJobBean 本身也实现了 Job 接口，其本质都是实现 Job 接口来定义任务。
 */

@Slf4j
public class WelcomeJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("这是一个quartz任务");
    }
}
