package com.secret.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.config.FileConfig;
import com.secret.dto.applet.ChatListDto;
import com.secret.entity.GroupChatEntity;
import com.secret.entity.MotorcadeEntity;
import com.secret.enums.applet.UnreadMessageEnum;
import com.secret.mapper.GroupChatMapper;
import com.secret.service.GroupChatMemberService;
import com.secret.service.GroupChatService;
import com.secret.service.GroupMsgContentService;
import com.secret.utils.ImageUtil;
import com.secret.utils.TransferUtils;
import com.secret.vo.applet.ChatListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
@Slf4j
public class GroupChatServiceImpl extends ServiceImpl<GroupChatMapper, GroupChatEntity> implements GroupChatService {

    @Resource
    private  GroupChatMapper groupChatMapper;

    @Resource
    private GroupMsgContentService groupMsgContentService;

    @Resource
    private GroupChatMemberService groupChatMemberService;

    @Resource
    private FileConfig fileConfig;

    /**
     * 根据用户id 获取聊天
     *
     * @param userId
     * @return list
     */
    @Override
    public List<ChatListVo> getChatByUserId(Integer userId) {
        List<ChatListDto> chatByUserId = groupChatMapper.getChatByUserId(userId);
        chatByUserId.forEach( chatListDto ->  {
            // 获取未读数量
            if( chatListDto.getMessageId() != null && !chatListDto.getLastMessageId().equals(chatListDto.getMessageId())){
                chatListDto.setUnreadMessage(UnreadMessageEnum.HAVE.getCode());
                Integer unreadTotal = groupMsgContentService.getUnreadTotal(chatListDto.getLastMessageId(), chatListDto.getChatId());
                chatListDto.setUnreadMessageQuantity(unreadTotal);
            }else{
                chatListDto.setUnreadMessage(UnreadMessageEnum.EMPTY.getCode());
            }
        } );
        return TransferUtils.transferList(chatByUserId, ChatListVo.class);
    }
    /**
     * 创建聊天室
     *
     * @param motorcadeEntity
     * @return
     */
    @Override
    public GroupChatEntity createGroupChat(MotorcadeEntity motorcadeEntity) {
        GroupChatEntity groupChatEntity = new GroupChatEntity();
        groupChatEntity.setGroupName(motorcadeEntity.getTitle());
        groupChatEntity.setMotorcadeId(motorcadeEntity.getId());
        save(groupChatEntity);
        return groupChatEntity;
    }

    /**
     * 更改头像
     *
     * @param chatId
     * @return
     */
    @Override
    public Boolean changeHeadPortrait(Integer chatId) {
        List<String> memberHeadPortrait = groupChatMemberService.getMemberHeadPortrait(chatId);
        String fileName = "GP-" + chatId+".jpg";
        try {
            boolean combinationOfhead = ImageUtil.getCombinationOfhead(memberHeadPortrait, fileConfig.getFileUrl(),fileName);
            if(combinationOfhead){
                boolean update = this.update(new LambdaUpdateWrapper<GroupChatEntity>().set(GroupChatEntity::getGroupHeadImg, fileName).eq(GroupChatEntity::getId, chatId));
                return update;
            }
            log.error("failed to generate avatar ,chat id {}",chatId);
        }catch (IOException e){
            log.error("error failed to generate avatar ,chat id {}",chatId,e);
        }
        return Boolean.FALSE;
    }

    /**
     * 根据用户id & 群聊id 获取聊天框
     *
     * @param userId
     * @param chatId
     */
    @Override
    public ChatListVo getChatByUIdAndCId(Integer userId, Integer chatId) {
       ChatListDto chatListDto = groupChatMapper.getChatByUIdAndCId(userId,chatId);
        // 获取未读数量
        if(!chatListDto.getLastMessageId().equals(chatListDto.getMessageId())){
            chatListDto.setUnreadMessage(UnreadMessageEnum.HAVE.getCode());
            Integer unreadTotal = groupMsgContentService.getUnreadTotal(chatListDto.getLastMessageId(), chatListDto.getChatId());
            chatListDto.setUnreadMessage(unreadTotal);
        }else{
            chatListDto.setUnreadMessage(UnreadMessageEnum.EMPTY.getCode());
        }
        ChatListVo chatListVo = new ChatListVo();
        TransferUtils.transferBean(chatListDto, chatListVo);
        return chatListVo;
    }
}
