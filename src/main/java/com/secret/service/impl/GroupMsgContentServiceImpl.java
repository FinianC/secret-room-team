package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.GroupMsgContentEntity;
import com.secret.mapper.GroupMsgContentMapper;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.service.GroupMsgContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
    public Integer getMaxIdByMotorcadeId(Integer groupId) {
        Integer maxIdByMotorcadeId = groupMsgContentMapper.getMaxIdByMotorcadeId(groupId);
        if(maxIdByMotorcadeId==null){
            maxIdByMotorcadeId =0;
        }
        return maxIdByMotorcadeId;
    }

    /**
     * 获取当前未读消息数量
     *
     * @param lastMessageId
     * @param chatId
     * @return
     */
    @Override
    public Integer getUnreadTotal(Integer lastMessageId, Integer chatId) {
       return groupMsgContentMapper.getUnreadTotal(lastMessageId, chatId);
    }

    /**
     * 查询聊天框信息
     *
     * @param page
     * @param chatId
     * @return
     */
    @Override
    public Page<GroupMsgContentVo> pageByChatId(Page page, Integer chatId) {
        Page<GroupMsgContentVo> groupMsgContentVoPage = groupMsgContentMapper.pageByChatId(page, chatId);
        Collections.reverse(groupMsgContentVoPage.getRecords());
        return groupMsgContentVoPage;
    }


}
