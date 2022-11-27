package com.secret.service;

import com.secret.model.entity.FleetChangeMessageEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-26
 */
public interface FleetChangeMessageService extends IService<FleetChangeMessageEntity> {


    void fleetChanges(Integer motorcadeId,Integer type,Integer userId);


}
