package com.jascola.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job importJob;

    public JobParameters jobParameters;
    /*@Scheduled(fixedRate = 5000)
    public void sout() throws Exception{
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(importJob,jobParameters);
    }*/

    @Scheduled(cron = "0 19 19 ? * *")
    public void sout2(){

        System.out.println("I am fine,thank you");
    }
}
