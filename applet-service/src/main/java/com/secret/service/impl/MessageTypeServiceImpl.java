package com.secret.service.impl;

import com.secret.entity.MessageTypeEntity;
import com.secret.mapper.MessageTypeMapper;
import com.secret.service.MessageTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息类型 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
@Service
public class MessageTypeServiceImpl extends ServiceImpl<MessageTypeMapper, MessageTypeEntity> implements MessageTypeService {

}
