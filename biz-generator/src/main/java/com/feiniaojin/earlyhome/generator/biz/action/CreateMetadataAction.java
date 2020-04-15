package com.feiniaojin.earlyhome.generator.biz.action;

import com.feiniaojin.earlyhome.generator.biz.bean.ConfigBean;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateState;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class CreateMetadataAction implements Action<GenerateState, GenerateEvent> {

    Gson gson = new Gson();

    @Override
    public void execute(StateContext<GenerateState, GenerateEvent> context) {
        ConfigBean configBean = (ConfigBean) context.getMessageHeaders().get("configBean");

        log.info("configBean=[{}]", gson.toJson(configBean));
    }
}
