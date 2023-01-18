package com.secret.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.mapper.GroupMsgContentMapper;
import com.secret.model.entity.GroupMsgContentEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secret.model.vo.GroupMsgContentVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 群聊消息 服务类
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
public interface GroupMsgContentService extends IService<GroupMsgContentEntity> {

   Integer getMaxIdByMotorcadeId(Integer motorcadeId);

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
   Page<GroupMsgContentVo> pageByChatId(Page page , Integer chatId);



}
