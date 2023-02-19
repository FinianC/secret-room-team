package com.secret.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.secret.constant.RS;
import com.secret.model.dto.User;
import com.secret.model.dto.WebSocketCommand;
import com.secret.model.entity.*;
import com.secret.model.enums.ChatListCommandEnum;
import com.secret.model.enums.GroupMessageEnum;
import com.secret.model.enums.WSClientTypeEnum;
import com.secret.model.params.ChatListParam;
import com.secret.model.params.GroupMsgContentParam;
import com.secret.model.vo.ChatListVo;
import com.secret.model.vo.GroupMessageVo;
import com.secret.model.vo.GroupMsgContentVo;
import com.secret.model.vo.UserVo;
import com.secret.service.*;
import com.secret.utils.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author cd
 * @date 2020/6/16 - 23:34
 */
@Controller
@Slf4j
public class WsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    GroupMsgContentService groupMsgContentService;

    @Autowired
    private GroupChatService groupChatService;


    @Autowired
    private GroupChatMemberService groupChatMemberService;


    EmojiConverter emojiConverter = EmojiConverter.getInstance();



    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 群聊的消息接受与转发
     * @param groupMsgContent
     */
    @MessageMapping("/chat/{chatId}")
    @Transactional
    public void handleGroupMessage(@Header String token , @DestinationVariable Integer chatId , GroupMsgContentParam groupMsgContent){
      UserVo user = (UserVo) UserLoginUtils.getUserInfo(token).getUser();
      GroupChatEntity groupChatEntity  = groupChatService.getById(chatId);
      Assert.notNull(groupChatEntity, RS.GROUP_CHAT_NOT_EXIST.message());

      //处理emoji内容,转换成unicode编码
      groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
      GroupMsgContentEntity groupMsgContentEntity = new GroupMsgContentEntity();
      TransferUtils.transferBean(groupMsgContent,groupMsgContentEntity);

        GroupChatMemberEntity groupChatMemberEntity = groupChatMemberService.getOne(new LambdaQueryWrapper<GroupChatMemberEntity>()
                .eq(GroupChatMemberEntity::getUserId, user.getId())
                .eq(GroupChatMemberEntity::getGroupId, groupChatEntity.getId()));
        LocalDateTime now = DateUtil.now();
        /**
         *  查询20分钟前的显示时间 如果有 则当前消息与前面共享显示时间 如果无则设置当前时间为显示时间
          */
        LocalDateTime localDateTime = now.minusMinutes(20);
        int count = groupMsgContentService.count(new LambdaQueryWrapper<GroupMsgContentEntity>().eq(GroupMsgContentEntity::getGroupId,chatId).gt(GroupMsgContentEntity::getDisplayTime, localDateTime));
        if(count<1){
            groupMsgContentEntity.setDisplayTime(now);
        }
        groupMsgContentEntity.setGroupId(chatId);
      groupMsgContentEntity.setCreateTime(now);
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
        groupMsgContentVo.setMotorcadeId(groupChatEntity.getMotorcadeId());
        // 更新聊天框
        toUpdateChatList(chatId);
        String destination = "/topic/chat/"+chatId;
        //转发该条数据
        simpMessagingTemplate.convertAndSend(destination,groupMsgContentVo);
    }

    @SubscribeMapping("/topic/test")
    public String subscribeTopic(){
        System.out.println("被订阅...");
        return "订阅成功";
    }

    /**
     * 更新聊天框
     * @param chatId
     */
    public void toUpdateChatList(Integer chatId){
        List<UserEntity> userEntities = groupChatMemberService.getUOpenIdByCId(chatId);
        userEntities.forEach( user -> {
            String token = RedisUtils.get(user.getOpenId(), String.class);
            if(StringUtils.isNotEmpty(token)){
                ChatListVo chatByUIdAndCId = groupChatService.getChatByUIdAndCId(user.getId(), chatId);
                simpMessagingTemplate.convertAndSendToUser(token, "/toUpdate/chatList",chatByUIdAndCId);
            }
        } );
    }


    /**
     * 接收用户信息
     * */
    @MessageMapping(value = "/principal")
    public void principal(@Header String token, WebSocketCommand webSocketCommand) {
        User user = WebSocketUser.getConcurrentHashMap().remove(token);

    }

  /**
   * 更新
   */
  @MessageMapping("/toUpdate/chatList")
  public void getChatList(User principal) {
      User principal1 = (User) principal;
      simpMessagingTemplate.convertAndSend( "/topic/chat",principal1);
  }
}
