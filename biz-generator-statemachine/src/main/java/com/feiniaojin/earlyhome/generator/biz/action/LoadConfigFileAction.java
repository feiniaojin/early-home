package com.feiniaojin.earlyhome.generator.biz.action;

import com.feiniaojin.earlyhome.generator.biz.bean.ConfigBean;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateState;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class LoadConfigFileAction implements Action<GenerateState, GenerateEvent> {

    Gson gson = new Gson();

    @Override
    public void execute(StateContext<GenerateState, GenerateEvent> context) {

        try {
            String path = this.getClass().getResource("/").getPath();
            String jsonConfigFilePath = path + "biz-generator.json";
            log.info("path={}", path);
            log.info("jsonConfigFilePath={}", jsonConfigFilePath);
            String configStr = FileUtils.readFileToString(new File(jsonConfigFilePath), Charset.forName("UTF-8"));
            ConfigBean configBean = gson.fromJson(configStr, ConfigBean.class);

            ConfigBean bean = (ConfigBean) context.getMessageHeaders().get("configBean");

            Message<GenerateEvent> message = MessageBuilder.withPayload(GenerateEvent.TO_CREATE_METADATA).setHeader("configBean", configBean).build();
            StateMachine<GenerateState, GenerateEvent> stateMachine = context.getStateMachine();
            stateMachine.start();
            boolean b = stateMachine.sendEvent(message);
            log.info("b={}",b);
        } catch (IOException e) {
            log.error("Can't find biz-generator.json", e);
        }
    }
}
