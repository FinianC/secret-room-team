package com.secret.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.dto.applet.ChatListDto;
import com.secret.entity.GroupChatEntity;
import org.apache.ibatis.annotations.Param;

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
    List<ChatListDto> getChatByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id & 群聊id 获取聊天框
     * @param userId
     * @param chatId
     * @return
     */
    ChatListDto getChatByUIdAndCId(@Param("userId") Integer userId, @Param("chatId") Integer chatId);
}
