package com.secret.mapper;

import com.secret.model.dto.ChatListDto;
import com.secret.model.entity.GroupChatEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
public interface GroupChatMapper extends BaseMapper<GroupChatEntity> {


    /**
     * 根据用户id查询聊天框
     * @param userId
     * @return
     */
    List<ChatListDto> getChatByUserId(Integer userId);
}
