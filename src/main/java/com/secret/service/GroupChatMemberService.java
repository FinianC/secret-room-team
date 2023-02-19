package com.secret.service;

import com.secret.model.entity.GroupChatMemberEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.entity.UserEntity;
import com.secret.model.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
public interface GroupChatMemberService extends IService<GroupChatMemberEntity> {

    /**
     * 是群成员
     * @param chatId
     * @return
     */
    Boolean isGroupMember(Integer userId,Integer chatId);

    /**
     * 加入群聊
     * @param userId
     * @param chatId
     * @return
     */
    GroupChatMemberEntity joinGroupChat(Integer userId, Integer chatId);

    /**
     * 加入群聊&群主
     * @param userId
     * @param chatId
     * @param isGroupLeader
     * @return
     */
    GroupChatMemberEntity joinGroupChat(Integer userId,Integer chatId,Boolean isGroupLeader);

    /**
     * 获取群成员头像
     * @param chatId
     * @return
     */
    List<String> getMemberHeadPortrait(Integer chatId);

    /**
     * 离开聊天室
     * @param userId
     * @param chatId
     * @return
     */
    Boolean leaveGroupChat(Integer userId, Integer chatId);

    /**
     * 根据群成员id获取userId
     * @param chatId
     * @return
     */
    List<Integer> getUIdByCId(Integer chatId);

    /**
     * 根据群成员获取openid
     * @param chatId
     * @return
     */
    List<UserEntity> getUOpenIdByCId(Integer chatId);

}
