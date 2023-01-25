package com.secret.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.secret.constant.RS;
import com.secret.model.dto.User;
import com.secret.model.entity.GroupChatEntity;
import com.secret.model.entity.GroupChatMemberEntity;
import com.secret.model.entity.GroupMsgContentEntity;
import com.secret.model.entity.MotorcadeEntity;
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
import com.secret.utils.DateUtil;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private SimpUserRegistry userRegistry;

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 群聊的消息接受与转发
     * @param groupMsgContent
     */
    @MessageMapping("/chat/{chatId}")
    @Transactional
    public void handleGroupMessage( User u,@DestinationVariable Integer chatId ,GroupMsgContentParam groupMsgContent){
      UserVo user = (UserVo) UserLoginUtils.getUserInfo(u.getUsername()).getUser();
      GroupChatEntity groupChatEntity  = groupChatService.getById(chatId);
      Assert.notNull(groupChatEntity, RS.GROUP_CHAT_NOT_EXIST.message());

      //处理emoji内容,转换成unicode编码
      groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
      GroupMsgContentEntity groupMsgContentEntity = new GroupMsgContentEntity();
      TransferUtils.transferBean(groupMsgContent,groupMsgContentEntity);

        GroupChatMemberEntity groupChatMemberEntity = groupChatMemberService.getOne(new LambdaQueryWrapper<GroupChatMemberEntity>()
                .eq(GroupChatMemberEntity::getUserId, user.getId())
                .eq(GroupChatMemberEntity::getGroupId, groupChatEntity.getId()));

        groupMsgContentEntity.setGroupId(chatId);
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
        groupMsgContentVo.setMotorcadeId(groupChatEntity.getMotorcadeId());
        // 更新聊天框
        toUpdateChatList(chatId);
        //转发该条数据
      simpMessagingTemplate.convertAndSend("/topic/chat/"+chatId,groupMsgContentVo);
    }

    /**
     * 更新聊天框
     * @param chatId
     */
    public void toUpdateChatList(Integer chatId){
        List<Integer> uIdByCId = groupChatMemberService.getUIdByCId(chatId);
        List<SimpUser> simpUsers = userRegistry.getUsers().stream().filter(simpUser -> {
            User principal = (User) simpUser.getPrincipal();
            return WSClientTypeEnum.CHAT_LIST.getCode().equals(principal.getType()) && uIdByCId.contains(principal.getId());
        }).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(simpUsers)){
            simpUsers.forEach( uId -> {
                User uId1 = (User) uId.getPrincipal();
                ChatListVo chatByUIdAndCId = groupChatService.getChatByUIdAndCId(uId1.getId(), chatId);
                simpMessagingTemplate.convertAndSendToUser(uId.getName(), "/toUpdate/chatList",chatByUIdAndCId);
            });
        }
    }


    /**
     * 接收用户信息
     * */
//    @MessageMapping(value = "/principal")
//    public void test(Principal principal) {
//        log.info("当前在线人数:" + userRegistry.getUserCount());
//        int i = 1;
//        for (SimpUser user : userRegistry.getUsers()) {
//            log.info("用户" + i++ + "---" + user);
//        }
//        //发送消息给指定用户
//        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message","服务器主动推的数据");
//    }

  /**
   * 更新
   */
  @MessageMapping("/toUpdate/chatList")
  public void getChatList(User principal) {
      User principal1 = (User) principal;
      simpMessagingTemplate.convertAndSend( "/topic/chat",principal1);
  }
}
