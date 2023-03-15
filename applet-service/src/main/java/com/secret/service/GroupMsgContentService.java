package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.entity.GroupMsgContentEntity;
import com.secret.vo.applet.GroupMsgContentVo;


/**
 * <p>
 * 群聊消息 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
public interface GroupMsgContentService extends IService<GroupMsgContentEntity> {

   /**
    * 根据聊天室 获取最新消息id
    * @param chatId
    * @return
    */
   Integer getMaxIdByMotorcadeId(Integer chatId);

   /**
    *  获取当前未读消息数量
    * @param lastMessageId
    * @param chatId
    * @return
    */
   Integer getUnreadTotal(Integer lastMessageId , Integer chatId);

   /**
    * 查询聊天框信息
    * @param page
    * @param chatId
    * @return
    */
   Page<GroupMsgContentVo> pageByChatId(Page<GroupMsgContentVo> page , Integer chatId);



}
