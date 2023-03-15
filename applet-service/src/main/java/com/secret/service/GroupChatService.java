package com.secret.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.entity.GroupChatEntity;
import com.secret.entity.MotorcadeEntity;
import com.secret.vo.applet.ChatListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
public interface GroupChatService extends IService<GroupChatEntity> {

    /**
     * 根据用户id 获取聊天
     * @param userId
     * @return
     */
    List<ChatListVo> getChatByUserId(Integer userId);

    /**
     * 创建聊天室
     * @param motorcadeEntity
     * @return
     */
    GroupChatEntity createGroupChat(MotorcadeEntity motorcadeEntity);

    /**
     * 更改头像
     * @param chatId
     * @return
     */
    Boolean changeHeadPortrait(Integer chatId);


    /**
     * 根据用户id & 群聊id 获取聊天框
     * @param userId
     * @param chatId
     */
    ChatListVo getChatByUIdAndCId(Integer userId ,Integer chatId);


}
