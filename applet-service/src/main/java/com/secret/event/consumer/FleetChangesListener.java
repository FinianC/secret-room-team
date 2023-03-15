package com.secret.event.consumer;

import com.secret.dto.applet.FleetChangesEventMessage;
import com.secret.event.FleetChangesEvent;
import com.secret.service.FleetChangeMessageService;
import com.secret.service.MotorcadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息通知监听
 */
@Component
public class FleetChangesListener implements ApplicationListener<FleetChangesEvent> {

    private static final Logger logger = LoggerFactory.getLogger(FleetChangesListener.class);

    @Autowired
    private MotorcadeService motorcadeService;

    @Resource
    private FleetChangeMessageService fleetChanges;

    /**
     * 注解 @Async 表明该方法使用异步执行
     */
    //@Async
    @Override
    public void onApplicationEvent(FleetChangesEvent fleetChangesEvent) {
        FleetChangesEventMessage fleetChangesEventMessage = fleetChangesEvent.getFleetChangesEventMessage();
        fleetChanges.fleetChanges(fleetChangesEventMessage.getFleetId(),fleetChangesEventMessage.getEventType(),fleetChangesEventMessage.getUserId());
        logger.info("event message is : {}", fleetChangesEventMessage.getFleetId());
    }

}
