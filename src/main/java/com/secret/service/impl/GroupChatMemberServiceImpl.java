package com.secret.service.impl;

import com.secret.model.entity.GroupChatMemberEntity;
import com.secret.mapper.GroupChatMemberMapper;
import com.secret.service.GroupChatMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
@Service
public class GroupChatMemberServiceImpl extends ServiceImpl<GroupChatMemberMapper, GroupChatMemberEntity> implements GroupChatMemberService {


    @Autowired
    private GroupChatMemberMapper groupChatMemberMapper;


    /**
     * 是群成员
     *
     * @param chatId
     * @return
     */
    @Override
    public Boolean isGroupMember(Integer userId,Integer chatId) {
        Integer groupMember = groupChatMemberMapper.isGroupMember(userId, chatId);
        return groupMember > 0;
    }
}
