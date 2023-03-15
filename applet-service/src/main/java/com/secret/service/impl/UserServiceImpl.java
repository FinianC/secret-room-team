package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secret.entity.UserEntity;
import com.secret.mapper.UserMapper;
import com.secret.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public String getOpenIdById(Integer userId) {
       return getOne(new LambdaQueryWrapper<UserEntity>().select(UserEntity::getOpenId).eq(UserEntity::getId, userId)).getOpenId();
    }
}
