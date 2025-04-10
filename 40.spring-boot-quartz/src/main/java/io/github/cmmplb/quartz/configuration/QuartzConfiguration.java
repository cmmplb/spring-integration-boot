package io.github.cmmplb.quartz.configuration;

import io.github.cmmplb.quartz.job.DongAoJob;
import io.github.cmmplb.quartz.job.WelcomeJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author penglibo
 * @date 2023-02-09 14:45:38
 * @since jdk 1.8
 * RAMJobStore, 就是将任务相关信息保存在内存里, 应用重启后, 定时任务信息将会丢失. 
 */

@Configuration
public class QuartzConfiguration {

    @Autowired
    private DataSource dataSource;

    /**
     * 代码启用WelcomeJob定时任务
     */
    @PostConstruct
    public void init() {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //启动
            scheduler.start();
            //新建一个 Job WelcomeJob
            JobDetail job = JobBuilder.newJob(WelcomeJob.class)
                    .withIdentity("mySimpleJob", "simpleGroup")
                    .build();

            // 触发器 定义多长时间触发 JobDetail
            Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
                    .withIdentity("simpleTrigger", "simpleGroup")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            // 绑定工厂
            scheduler.scheduleJob(job, trigger);
            //关闭
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    @Bean
    public JobDetail jobDetail() {
        //指定任务描述具体的实现类
        return JobBuilder.newJob(DongAoJob.class)
                // 指定任务的名称
                .withIdentity("dongAoJob")
                // 任务描述
                .withDescription("任务描述：用于输出冬奥欢迎语")
                // 每次任务执行后进行存储
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        //创建触发器
        return TriggerBuilder.newTrigger()
                // 绑定工作任务
                .forJob(jobDetail())
                // 每隔 5 秒执行一次 job
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();
    }


    @Bean
    public JobFactory jobFactory() {
        return new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                return super.createJobInstance(bundle);
            }
        };
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }
}
