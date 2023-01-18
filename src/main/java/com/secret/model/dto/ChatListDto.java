package com.secret.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: secret-room
 * @description: 聊天框dto
 * @author: 陈迪
 * @create: 2023/01/17 15:37
 */
@Data
@ApiModel("聊天框dto")
public class ChatListDto {

    @ApiModelProperty("群聊id")
    private Integer chatId;

    @ApiModelProperty("群聊名称")
    private String chatName;

    @ApiModelProperty("是否禁言")
    private Integer banned;

    @ApiModelProperty("群聊头像")
    private String groupHeadImg;

    @ApiModelProperty("日期")
    private LocalDateTime dateTime;

    @ApiModelProperty(value = "未读消息 1有 0无 ",notes = "1时展示小红点")
    private Integer unreadMessage;

    @ApiModelProperty(value = " 未读消息数量")
    private Integer unreadMessageQuantity;

    @ApiModelProperty(value = "当前用户读取的最后一次消息id")
    private Integer  lastMessageId;

    @ApiModelProperty(value = "消息类型")
    private Integer  messageTypeId;

    @ApiModelProperty(value = "消息id")
    private Integer  messageId;

    @ApiModelProperty(value = "消息")
    private String  message;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
