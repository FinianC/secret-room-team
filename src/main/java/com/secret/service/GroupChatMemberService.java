package com.secret.service;

import com.secret.model.entity.GroupChatMemberEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
