package com.feiniaojin.earlyhome.generator.biz;

import com.feiniaojin.earlyhome.generator.biz.bean.ConfigBean;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
public class BizGeneratorApplication implements CommandLineRunner {

    public static void main(String[] args)  {
        ConfigurableApplicationContext context = SpringApplication.run(BizGeneratorApplication.class);

    }

    @Resource
    private StateMachine<GenerateState, GenerateEvent> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        Message<GenerateEvent> message = MessageBuilder.withPayload(GenerateEvent.TO_LOAD_CONFIG)
            .setHeader("configBean", new ConfigBean())
            .build();
        stateMachine.sendEvent(message);
//        stateMachine.sendEvent(GenerateEvent.TO_CREATE_METADATA);
//        stateMachine.sendEvent(GenerateEvent.TO_RENDER_TEMPLATE);
    }
}
