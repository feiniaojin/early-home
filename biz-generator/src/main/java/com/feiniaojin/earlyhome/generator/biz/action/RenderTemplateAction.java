package com.feiniaojin.earlyhome.generator.biz.action;

import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class RenderTemplateAction implements Action<GenerateState, GenerateEvent> {
    @Override
    public void execute(StateContext<GenerateState, GenerateEvent> context) {

    }
}
