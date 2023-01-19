package com.secret.mapper;

import com.secret.model.entity.GroupChatMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface GroupChatMemberMapper extends BaseMapper<GroupChatMemberEntity> {



    Integer isGroupMember(@Param("userId") Integer userId, @Param("chatId") Integer chatId);

    /**
     * 获取成员头像
     * @param chatId
     * @return
     */
    List<String> getMemberHeadPortrait(@Param("chatId") Integer chatId);
}
