package com.secret.service.impl;

import com.secret.model.entity.GroupChatMemberEntity;
import com.secret.mapper.GroupChatMemberMapper;
import com.secret.model.enums.GroupLeaderEnum;
import com.secret.model.vo.R;
import com.secret.service.GroupChatMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.service.GroupChatService;
import com.secret.service.GroupMsgContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private GroupMsgContentService groupMsgContentService;

    @Autowired
    private GroupChatService groupChatService;


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

    /**
     * 加入群聊
     *
     * @param userId
     * @param chatId
     * @return
     */
    @Override
    public GroupChatMemberEntity joinGroupChat(Integer userId, Integer chatId) {
        // 加入聊天室
        return  joinGroupChat(userId,chatId,Boolean.FALSE);
    }

    /**
     * 加入群聊&群主
     *
     * @param userId
     * @param chatId
     * @param isGroupLeader
     * @return
     */
    @Override
    public GroupChatMemberEntity joinGroupChat(Integer userId, Integer chatId, Boolean isGroupLeader) {
        // 加入聊天室
        Integer maxIdByGroupId = groupMsgContentService.getMaxIdByMotorcadeId(chatId);
        GroupChatMemberEntity groupChatMemberEntity = new GroupChatMemberEntity();
        groupChatMemberEntity.setFirstMessageId(maxIdByGroupId);
        groupChatMemberEntity.setLastMessageId(maxIdByGroupId);
        groupChatMemberEntity.setUserId(userId);
        groupChatMemberEntity.setGroupId(chatId);
        if(isGroupLeader){
            groupChatMemberEntity.setIsGroupLeader(GroupLeaderEnum.IS.getCode());
        }
        this.save(groupChatMemberEntity);
        groupChatService.changeHeadPortrait(chatId);
        return groupChatMemberEntity;
    }

    /**
     * 获取群成员头像
     *
     * @param chatId
     * @return
     */
    @Override
    public List<String> getMemberHeadPortrait(Integer chatId) {
        List<String> memberHeadPortrait = groupChatMemberMapper.getMemberHeadPortrait(chatId);
      return memberHeadPortrait.stream().map(filePath -> {
            if (filePath.indexOf("http") == -1) {
                filePath = R.redirect_url + filePath;
            }
            return filePath;
        }).collect(Collectors.toList());
    }
}
