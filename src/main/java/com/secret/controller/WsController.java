package com.secret.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.secret.constant.RS;
import com.secret.model.entity.GroupMsgContentEntity;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.model.enums.GroupMessageEnum;
import com.secret.model.params.GroupMsgContentParam;
import com.secret.model.vo.GroupMessageVo;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.model.vo.UserVo;
import com.secret.service.GroupMsgContentService;
import com.secret.service.MotorcadeService;
import com.secret.utils.DateUtil;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hai
 * @date 2020/6/16 - 23:34
 */
@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private MotorcadeService motorcadeService;

    @Autowired
    GroupMsgContentService groupMsgContentService;
    EmojiConverter emojiConverter = EmojiConverter.getInstance();

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 群聊的消息接受与转发
     * @param groupMsgContent
     */
    @MessageMapping("/ws/{motorcadeId}")
    public void handleGroupMessage(@PathVariable Integer motorcadeId , GroupMsgContentParam groupMsgContent){
      UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
      MotorcadeEntity motorcadeEntity = motorcadeService.getOne(new LambdaQueryWrapper<MotorcadeEntity>().select(MotorcadeEntity::getId).eq(MotorcadeEntity::getId,motorcadeId));
      Assert.notNull(motorcadeEntity, RS.GROUP_CHAT_NOT_EXIST.message());
      //处理emoji内容,转换成unicode编码
      groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
      GroupMsgContentEntity groupMsgContentEntity = new GroupMsgContentEntity();
      TransferUtils.transferBean(groupMsgContent,groupMsgContentEntity);
      //保证来源正确性，从Security中获取用户信息
      groupMsgContentEntity.setFromId(user.getId());
      groupMsgContentEntity.setFromName(user.getNickname());
      groupMsgContentEntity.setFromProfile(user.getHeaderImg());
      groupMsgContentEntity.setCreateTime(DateUtil.now());
      groupMsgContentEntity.setMotorcadeId(motorcadeId);
      //保存该条群聊消息记录到数据库中
      groupMsgContentService.save(groupMsgContentEntity);
      // 封装新消息
        GroupMsgContentVo groupMsgContentVo = new GroupMsgContentVo();
        TransferUtils.transferBean(groupMsgContentEntity,groupMsgContentVo);
        GroupMessageVo<GroupMsgContentVo> groupMessageVo = new GroupMessageVo();
        groupMessageVo.setType(GroupMessageEnum.NEW.getCode());
        groupMessageVo.setGroupMsgContentVo(groupMsgContentVo);
        //转发该条数据
      simpMessagingTemplate.convertAndSend("/topic/"+motorcadeId,groupMessageVo);
    }

//  /**
//   * 获取聊天列表
//   */
//  @MessageMapping("/ws/getChatBox")
//  public void getChatBox(){
//    UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
//
//    //转发该条数据
//    simpMessagingTemplate.convertAndSend("/topic/"+motorcadeId,groupMsgContent);
//  }
}
