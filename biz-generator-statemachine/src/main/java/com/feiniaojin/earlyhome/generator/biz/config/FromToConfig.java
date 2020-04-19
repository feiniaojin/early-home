package com.feiniaojin.earlyhome.generator.biz.config;

import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
@WithStateMachine(id="generateMachine")
@Slf4j
public class FromToConfig {

  @OnTransition(source = "LOAD_CONFIG", target = "CREATE_METADATA")
  public void confirm(Message<GenerateEvent> message) {
    MessageHeaders headers = message.getHeaders();
    log.info("---校验表单---");
  }
}
