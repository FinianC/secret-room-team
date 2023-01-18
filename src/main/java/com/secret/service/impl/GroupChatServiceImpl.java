package com.secret.service.impl;

import com.secret.model.dto.ChatListDto;
import com.secret.model.entity.GroupChatEntity;
import com.secret.mapper.GroupChatMapper;
import com.secret.model.enums.UnreadMessageEnum;
import com.secret.model.vo.ChatListVo;
import com.secret.service.GroupChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.service.GroupMsgContentService;
import com.secret.utils.TransferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
@Service
public class GroupChatServiceImpl extends ServiceImpl<GroupChatMapper, GroupChatEntity> implements GroupChatService {

    @Autowired
    private  GroupChatMapper groupChatMapper;

    @Autowired
    private GroupMsgContentService groupMsgContentService;

    /**
     * 根据用户id 获取聊天
     *
     * @param userId
     * @return
     */
    @Override
    public List<ChatListVo> getChatByUserId(Integer userId) {
        List<ChatListDto> chatByUserId = groupChatMapper.getChatByUserId(userId);
        chatByUserId.forEach( chatListDto ->  {
            // 获取未读数量
            if(!chatListDto.getLastMessageId().equals(chatListDto.getMessageId())){
                chatListDto.setUnreadMessage(UnreadMessageEnum.HAVE.getCode());
                Integer unreadTotal = groupMsgContentService.getUnreadTotal(chatListDto.getLastMessageId(), chatListDto.getChatId());
                chatListDto.setUnreadMessage(unreadTotal);
            }else{
                chatListDto.setUnreadMessage(UnreadMessageEnum.EMPTY.getCode());
            }
        } );
         List<ChatListVo> chatListVos = TransferUtils.transferList(chatByUserId, ChatListVo.class);
        return chatListVos;
    }
}
