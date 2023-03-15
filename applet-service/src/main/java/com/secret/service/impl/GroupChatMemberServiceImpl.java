package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.entity.GroupChatMemberEntity;
import com.secret.entity.UserEntity;
import com.secret.enums.applet.GroupLeaderEnum;
import com.secret.mapper.GroupChatMemberMapper;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupChatService;
import com.secret.service.GroupMsgContentService;
import com.secret.vo.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    private GroupChatMemberMapper groupChatMemberMapper;

    @Resource
    private GroupMsgContentService groupMsgContentService;

    @Resource
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
        if(Boolean.TRUE.equals(isGroupLeader)){
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
                filePath = R.redirectUrl + filePath;
            }
            return filePath;
        }).collect(Collectors.toList());
    }

    /**
     * 离开聊天室
     *
     * @param userId
     * @param chatId
     * @return
     */
    @Override
    public Boolean leaveGroupChat(Integer userId, Integer chatId) {
        boolean remove = this.remove(new LambdaQueryWrapper<GroupChatMemberEntity>()
                .eq(GroupChatMemberEntity::getGroupId, chatId)
                .eq(GroupChatMemberEntity::getUserId, userId));
        groupChatService.changeHeadPortrait(chatId);
        return remove;
    }

    /**
     * 根据群id获取userId
     *
     * @param chatId
     * @return
     */
    @Override
    public List<Integer> getUIdByCId(Integer chatId) {
        List<GroupChatMemberEntity> list = this.list(new LambdaQueryWrapper<GroupChatMemberEntity>().select(GroupChatMemberEntity::getUserId).eq(GroupChatMemberEntity::getGroupId, chatId));
        return list.stream().map(GroupChatMemberEntity::getUserId).collect(Collectors.toList());
    }

    /**
     * 获取openid
     * @param chatId
     * @return
     */
    @Override
    public List<UserEntity> getUOpenIdByCId(Integer chatId) {
       return groupChatMemberMapper.getUOpenIdByCId(chatId);
    }
}
