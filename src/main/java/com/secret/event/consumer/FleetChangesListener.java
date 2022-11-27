package com.secret.event.consumer;

import com.secret.event.FleetChangesEvent;

import com.secret.model.dto.FleetChangesEventMessage;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.model.enums.FleetChangesEnum;
import com.secret.service.MotorcadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息通知监听
 */
@Component
public class FleetChangesListener implements ApplicationListener<FleetChangesEvent> {

    private static final Logger logger = LoggerFactory.getLogger(FleetChangesListener.class);

    @Autowired
    private MotorcadeService motorcadeService;

    /**
     * 注解 @Async 表明改方法使用异步执行
     */
    //@Async
    @Override
    public void onApplicationEvent(FleetChangesEvent fleetChangesEvent) {
        FleetChangesEventMessage fleetChangesEventMessage = fleetChangesEvent.getFleetChangesEventMessage();
        MotorcadeEntity motorcadeEntity = motorcadeService.getById(fleetChangesEventMessage.getFleetId());
        if( FleetChangesEnum.JOIN.getCode().equals( fleetChangesEventMessage.getFleetId())){

        } else if(FleetChangesEnum.LEAVE.getCode().equals( fleetChangesEventMessage.getFleetId())) {

        }

        logger.info("event message is : {}", fleetChangesEventMessage.getFleetId());
    }

}
