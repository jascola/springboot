package com.jascola.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    /**
     * 监听test主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"topic1"})
    public void consumer(String message){
        log.info("test topic message : {}", message);
    }
}
