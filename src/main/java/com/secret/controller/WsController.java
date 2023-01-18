package com.secret.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.secret.constant.RS;
import com.secret.model.entity.GroupChatEntity;
import com.secret.model.entity.GroupChatMemberEntity;
import com.secret.model.entity.GroupMsgContentEntity;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.model.enums.ChatListCommandEnum;
import com.secret.model.enums.GroupMessageEnum;
import com.secret.model.params.ChatListParam;
import com.secret.model.params.GroupMsgContentParam;
import com.secret.model.vo.ChatListVo;
import com.secret.model.vo.GroupMessageVo;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.model.vo.UserVo;
import com.secret.service.*;
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

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private JoinedMotorcadeService joinedMotorcadeService;

    @Autowired
    private GroupChatMemberService groupChatMemberService;


    EmojiConverter emojiConverter = EmojiConverter.getInstance();

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 群聊的消息接受与转发
     * @param groupMsgContent
     */
    @MessageMapping("/ws/{groupId}")
    public void handleGroupMessage(@PathVariable Integer groupId , GroupMsgContentParam groupMsgContent){
      UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
      GroupChatEntity groupChatEntity  = groupChatService.getById(groupId);
      Assert.notNull(groupChatEntity, RS.GROUP_CHAT_NOT_EXIST.message());

      //处理emoji内容,转换成unicode编码
      groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
      GroupMsgContentEntity groupMsgContentEntity = new GroupMsgContentEntity();
      TransferUtils.transferBean(groupMsgContent,groupMsgContentEntity);

        GroupChatMemberEntity groupChatMemberEntity = groupChatMemberService.getOne(new LambdaQueryWrapper<GroupChatMemberEntity>()
                .eq(GroupChatMemberEntity::getUserId, user.getId())
                .eq(GroupChatMemberEntity::getGroupId, groupChatEntity.getId()));

        groupMsgContentEntity.setGroupId(groupId);
      groupMsgContentEntity.setCreateTime(DateUtil.now());
      groupMsgContentEntity.setMemberId(groupChatMemberEntity.getId());
      //保存该条群聊消息记录到数据库中
      groupMsgContentService.save(groupMsgContentEntity);
      // 封装新消息
        GroupMsgContentVo groupMsgContentVo = new GroupMsgContentVo();
        TransferUtils.transferBean(groupMsgContentEntity,groupMsgContentVo);
        groupMsgContentVo.setFromId(groupChatMemberEntity.getId());
        groupMsgContentVo.setUserId(user.getId());
        groupMsgContentVo.setFromName(user.getNickname());
        groupMsgContentVo.setFromProfile(user.getHeaderImg());

        //转发该条数据
      simpMessagingTemplate.convertAndSend("/topic/"+groupId,groupMsgContentVo);
    }

  /**
   * 获取聊天列表
   */
  @MessageMapping("/ws/{userId}/getChatList")
  public void getChatList(@PathVariable Integer userId) {

      UserVo user = (UserVo) UserLoginUtils.getUserInfo().getUser();
      List<ChatListVo> chatByUserId = groupChatService.getChatByUserId(user.getId());
      simpMessagingTemplate.convertAndSend("/topic/chat/"+userId,chatByUserId);
  }
}
