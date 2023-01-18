package com.secret.service;

import com.secret.model.entity.GroupChatEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.vo.ChatListVo;

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
}
