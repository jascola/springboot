package com.jascola.springboot.conf;

import com.jascola.listener.MyJobListener;
import com.jascola.model.entity.UserEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private Logger logger = LoggerFactory.getLogger(BatchConfig.class);
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 配置注册job的容器
     */
    @Bean
    public static JobRepository jobRepository(DataSource basicDataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(basicDataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType("mysql");
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * 配置启动job的接口
     */
    @Bean
    public static SimpleJobLauncher jobLauncher(DataSource basicDataSource, PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(basicDataSource, transactionManager));
        return jobLauncher;
    }

    /**
     * 配置要执行的job，包含多个step
     */
    @Bean
    public static Job importJob(JobBuilderFactory jobs, Step step1) {
        return jobs.get("selectJob").incrementer(new RunIdIncrementer()).flow(step1).end().listener(listener()).build();
    }

    /**
     * 配置job执行之前的监听
     */
    @Bean
    public static MyJobListener listener() {
        return new MyJobListener();
    }

    /**
     * 配置step,step包含 itemReader,itemProcessor,itemWriter
     */
    @Bean
    public static Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<UserEntity> reader, ItemWriter<UserEntity> writer, ItemProcessor<UserEntity, UserEntity> processor) {
        return stepBuilderFactory.get("step1").
                <UserEntity, UserEntity>chunk(65000).reader(reader).processor(processor).writer(writer).build();
    }

    /**
     * 配置itemReader
     */

    @Bean
    public ItemReader<UserEntity> reader(){
        MyBatisCursorItemReader<UserEntity> myBatisCursorItemReader = new MyBatisCursorItemReader<>();
        try {
            myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
            myBatisCursorItemReader.setQueryId("selectAll");
        }catch (Exception e){
            logger.info(e.getLocalizedMessage(),e);
        }
        return myBatisCursorItemReader;
    }

    /**
     * 配置itemProcessor
     */

    @Bean
    public  ItemProcessor<UserEntity, UserEntity> processor() throws Exception {
        ItemProcessorImp itemProcessorImp = new ItemProcessorImp();
        return itemProcessorImp;
    }

    /**
     * 配置itemWriter
     */

    @Bean
    public  ItemWriter<UserEntity> writer(){
        MyBatisBatchItemWriter<UserEntity> myBatisBatchItemWriter = new MyBatisBatchItemWriter<>();
        try{
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("insert");
        }catch (Exception e){
            logger.info(e.getLocalizedMessage(),e);
        }
        return myBatisBatchItemWriter;
    }

    class ItemProcessorImp implements ItemProcessor<UserEntity,UserEntity>{

        @Override
        public UserEntity process(UserEntity userEntity) throws Exception {
            UserEntity result  = new UserEntity();
            result.setUsername(userEntity.getUsername()+"cao ni");
            result.setPassword(userEntity.getPassword()+"go bi");
            return result;
        }
    }


}
