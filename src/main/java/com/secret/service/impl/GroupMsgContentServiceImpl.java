package com.secret.service.impl;

import com.secret.model.entity.GroupMsgContentEntity;
import com.secret.mapper.GroupMsgContentMapper;
import com.secret.service.GroupMsgContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊消息 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
@Service
public class GroupMsgContentServiceImpl extends ServiceImpl<GroupMsgContentMapper, GroupMsgContentEntity> implements GroupMsgContentService {


    @Autowired
    private GroupMsgContentMapper groupMsgContentMapper;


    @Override
    public Integer getMaxIdByMotorcadeId(Integer motorcadeId) {
        Integer maxIdByMotorcadeId = groupMsgContentMapper.getMaxIdByMotorcadeId(motorcadeId);
        if(maxIdByMotorcadeId==null){
            maxIdByMotorcadeId =0;
        }
        return maxIdByMotorcadeId;
    }
}
