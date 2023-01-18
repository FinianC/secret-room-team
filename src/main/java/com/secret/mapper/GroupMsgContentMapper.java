package com.secret.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.GroupMsgContentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.model.vo.GroupMsgContentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 群聊消息 Mapper 接口
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
public interface GroupMsgContentMapper extends BaseMapper<GroupMsgContentEntity> {

     Integer getMaxIdByMotorcadeId(@Param("groupId") Integer groupId);

     /**
      * 获取未读消息数量
      * @param lastMessageId
      * @param chatId
      * @return
      */
     Integer getUnreadTotal(@Param("lastMessageId") Integer lastMessageId, @Param("chatId") Integer chatId);

     /**
      * 分页查询聊天列表
      * @param page
      * @param chatId
      * @return
      */
     Page<GroupMsgContentVo> pageByChatId(@Param("page") Page page,@Param("chatId") Integer chatId);

}
