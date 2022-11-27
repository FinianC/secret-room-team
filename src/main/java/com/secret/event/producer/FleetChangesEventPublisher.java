package com.secret.event.producer;

import com.secret.event.FleetChangesEvent;
import com.secret.model.dto.EventMessage;
import com.secret.model.dto.FleetChangesEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FleetChangesEventPublisher<T> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(FleetChangesEventMessage fleetChangesEventMessage){
        applicationEventPublisher.publishEvent(new FleetChangesEvent(fleetChangesEventMessage));
    }
}
