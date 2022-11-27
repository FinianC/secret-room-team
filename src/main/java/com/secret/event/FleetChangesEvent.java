package com.secret.event;

import com.secret.model.dto.EventMessage;
import com.secret.model.dto.FleetChangesEventMessage;
import lombok.Getter;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.context.ApplicationEvent;

@Getter
public class FleetChangesEvent extends ApplicationEvent {

    private static final Logger logger = LoggerFactory.getLogger(FleetChangesEvent.class);

    /** * 接受信息 */
    private FleetChangesEventMessage fleetChangesEventMessage;

    public FleetChangesEvent( FleetChangesEventMessage fleetChangesEventMessage) {
        super(fleetChangesEventMessage);
        this.fleetChangesEventMessage = fleetChangesEventMessage;
    }
}

