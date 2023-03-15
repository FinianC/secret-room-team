package com.secret.service;

import com.secret.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 获取openid
     * @param userId
     * @return
     */
    String getOpenIdById(Integer userId);

}
