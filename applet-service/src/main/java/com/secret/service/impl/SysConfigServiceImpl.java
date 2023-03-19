package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.constant.SysConfigConstant;
import com.secret.entity.SysConfigEntity;
import com.secret.mapper.SysConfigMapper;
import com.secret.service.SysConfigService;
import com.secret.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2023-03-19
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigEntity> implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public Long getOrderTimeout() {
        Long orderTimeout =(Long) RedisUtils.get(SysConfigConstant.ORDER_TIMEOUT);
        if(orderTimeout == null ){
            String orderTimeoutStr = sysConfigMapper.getOrderTimeout(SysConfigConstant.ORDER_TIMEOUT);
            if(orderTimeoutStr !=null ){
                orderTimeout= Long.valueOf(orderTimeoutStr);
                RedisUtils.set(SysConfigConstant.ORDER_TIMEOUT,orderTimeout,SysConfigConstant.CACHE_EXPIRE_TIME);
            }
        }
        return  orderTimeout;
    }

    @Override
    public Long getQrCodeTimeout() {
        Long qrCodeExpire =(Long) RedisUtils.get(SysConfigConstant.QR_CODE_EXPIRE);
        if(qrCodeExpire == null ){
            String qrCodeExpireStr = sysConfigMapper.getOrderTimeout(SysConfigConstant.QR_CODE_EXPIRE);
            if(qrCodeExpireStr !=null ){
                qrCodeExpire= Long.valueOf(qrCodeExpireStr);
                RedisUtils.set(SysConfigConstant.ORDER_TIMEOUT,qrCodeExpire);
            }
        }
        return  qrCodeExpire;
    }
}
