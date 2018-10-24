package com.jascola.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    Logger logger = LoggerFactory.getLogger(Receiver.class);
    @JmsListener(destination = "gogogo")
    public void receiveMessage(String message){
        logger.info("接收到消息《"+message+"》");
    }
}
