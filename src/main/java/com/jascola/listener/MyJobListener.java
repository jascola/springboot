package com.jascola.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {
    private Logger logger = LoggerFactory.getLogger(MyJobListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("任务处理开始了......");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("任务处理结束了......");
    }
}
