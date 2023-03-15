package com.secret.event.producer;

import com.secret.dto.applet.FleetChangesEventMessage;
import com.secret.event.FleetChangesEvent;
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
