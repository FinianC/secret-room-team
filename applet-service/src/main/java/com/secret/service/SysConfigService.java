package com.secret.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.entity.SysConfigEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2023-03-19
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
     * 获取订单超时时间配置
     * @return
     */
    Long getOrderTimeout();

    /**
     * 获取二维码过期时间配置
     * @return
     */
    Long getQrCodeTimeout();

}
