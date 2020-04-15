package com.feiniaojin.earlyhome.generator.biz.config;

import com.feiniaojin.earlyhome.generator.biz.action.CreateMetadataAction;
import com.feiniaojin.earlyhome.generator.biz.action.LoadConfigFileAction;
import com.feiniaojin.earlyhome.generator.biz.action.RenderTemplateAction;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateEvent;
import com.feiniaojin.earlyhome.generator.biz.enums.GenerateState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StatemachineConfigurer extends EnumStateMachineConfigurerAdapter<GenerateState, GenerateEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<GenerateState, GenerateEvent> states)
            throws Exception {
        states.withStates()
                .initial(GenerateState.INIT)
                .states(EnumSet.allOf(GenerateState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<GenerateState, GenerateEvent> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(GenerateState.INIT).target(GenerateState.LOAD_CONFIG)
                .event(GenerateEvent.TO_LOAD_CONFIG).action(loadConfigFileAction())
                .and()
                .withExternal()
                .source(GenerateState.LOAD_CONFIG).target(GenerateState.CREATE_METADATA)
                .event(GenerateEvent.TO_CREATE_METADATA).action(createMetadataAction())
                .and()
                .withExternal()
                .source(GenerateState.CREATE_METADATA).target(GenerateState.RENDER_TEMPLATE)
                .event(GenerateEvent.TO_RENDER_TEMPLATE).action(renderTemplateAction())
        ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<GenerateState, GenerateEvent> config)
            throws Exception {
        config.withConfiguration().machineId("generateMachine");
    }

    @Bean
    Action<GenerateState, GenerateEvent> loadConfigFileAction() {
        return new LoadConfigFileAction();
    }

    @Bean
    Action<GenerateState, GenerateEvent> createMetadataAction() {
        return new CreateMetadataAction();
    }

    @Bean
    Action<GenerateState, GenerateEvent> renderTemplateAction() {
        return new RenderTemplateAction();
    }

}
