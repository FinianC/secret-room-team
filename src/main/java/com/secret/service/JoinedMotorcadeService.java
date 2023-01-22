package com.secret.service;

import com.secret.model.entity.JoinedMotorcadeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.omg.CORBA.INTERNAL;

/**
 * <p>
 * 已加入的车队 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface JoinedMotorcadeService extends IService<JoinedMotorcadeEntity> {


    /**
     * 离开车队
     * @param userId
     * @param motorcadeId
     * @return
     */
    Boolean leave(Integer userId, Integer motorcadeId);

    /**
     * 加入车队
     * @param userId
     * @param motorcadeId
     * @return
     */
    JoinedMotorcadeEntity join(Integer userId ,Integer motorcadeId);

}
