package com.nht.orderservice.config;

import com.nht.orderservice.entity.OrderStatus;
import events.order.OrderEventType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEventType> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderEventType> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEventType> states) throws Exception {
        states.withStates()
                .initial(OrderStatus.NEW)
                .end(OrderStatus.DELIVERED)
                .end(OrderStatus.CANCELLED)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEventType> transitions) throws Exception {
        // TODO: add actions for 'from error state to cancelled state' transitions.
        //  The action is just like other event logs: save the orderId, eventType, eventId, payload, etc.
        //  An example code is here "https://chatgpt.com/c/68a1fca2-41c8-832b-81cb-342084d889a1"
        transitions
                // --- NEW to VALIDATED - VALIDATION_ERROR ---
                .withExternal()
                    .source(OrderStatus.NEW).target(OrderStatus.VALIDATED).event(OrderEventType.VALIDATION_PASSED)
                    .and()
                .withExternal()
                    .source(OrderStatus.NEW).target(OrderStatus.VALIDATION_ERROR).event(OrderEventType.VALIDATION_FAILED)
                    .and()
                // --- VALIDATED to PENDING_INVENTORY - ALLOCATION_ERROR - ALLOCATED ---
                .withExternal()
                    .source(OrderStatus.VALIDATED).target(OrderStatus.ALLOCATION_ERROR).event(OrderEventType.ALLOCATION_FAILED)
                    .and()
                .withExternal()
                    .source(OrderStatus.VALIDATED).target(OrderStatus.PENDING_INVENTORY).event(OrderEventType.ALLOCATION_NO_INVENTORY)
                    .and()
                .withExternal()
                    .source(OrderStatus.VALIDATED).target(OrderStatus.ALLOCATED).event(OrderEventType.ALLOCATION_SUCCESS)
                    .and()
                .withExternal()
                    .source(OrderStatus.PENDING_INVENTORY).target(OrderStatus.ALLOCATION_ERROR).event(OrderEventType.ALLOCATION_FAILED)
                    .and()
                .withExternal()
                    .source(OrderStatus.PENDING_INVENTORY).target(OrderStatus.ALLOCATED).event(OrderEventType.ALLOCATION_SUCCESS)
                    .and()
                // --- ALLOCATED to PICKED_UP - DELIVERY_ERROR ---
                .withExternal()
                    .source(OrderStatus.ALLOCATED).target(OrderStatus.PICKED_UP).event(OrderEventType.PICKUP_ORDER)
                    .and()
                .withExternal()
                    .source(OrderStatus.PICKED_UP).target(OrderStatus.DELIVERY_ERROR).event(OrderEventType.DELIVERY_FAILED)
                    .and()
                .withExternal()
                    .source(OrderStatus.PICKED_UP).target(OrderStatus.DELIVERED).event(OrderEventType.DELIVERY_SUCCESS)
                .and()
                // --- ERROR states to CANCELLED ---
                .withExternal()
                    .source(OrderStatus.VALIDATION_ERROR).target(OrderStatus.CANCELLED).and()
                .withExternal()
                    .source(OrderStatus.ALLOCATION_ERROR).target(OrderStatus.CANCELLED).and()
                .withExternal()
                    .source(OrderStatus.DELIVERY_ERROR).target(OrderStatus.CANCELLED).and();
    }

    /**
     * State machine listener to log state changes.
     * @return the listener instance.
     */
    @Bean
    public StateMachineListener<OrderStatus, OrderEventType> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OrderStatus, OrderEventType> from, State<OrderStatus, OrderEventType> to) {
                // TODO: Implement logging or handling logic here
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
