package com.jascola.springboot.conf;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {
    private final static String queueName = "spring-queue";
    private final static String topicName = "spring-topic";
    @Bean(name="activeMQQueue")
    public static ActiveMQQueue activeMQQueue(){
        ActiveMQQueue queue = new ActiveMQQueue(queueName);
        return queue;
    }
    @Bean(name="activeMQTopic")
    public static ActiveMQTopic activeMQTopic(){
        ActiveMQTopic topic = new ActiveMQTopic(topicName);
        return topic;
    }

}
