package com.secret.service;

import com.secret.mapper.GroupMsgContentMapper;
import com.secret.model.entity.GroupMsgContentEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 群聊消息 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
public interface GroupMsgContentService extends IService<GroupMsgContentEntity> {

   Integer getMaxIdByMotorcadeId(Integer motorcadeId);

}
