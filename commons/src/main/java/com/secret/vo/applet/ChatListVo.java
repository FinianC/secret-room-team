package com.secret.vo.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("群聊")
public class ChatListVo {

    @ApiModelProperty("群聊id")
    private Integer chatId;

    @ApiModelProperty("群聊名称")
    private String chatName;

    @ApiModelProperty("日期")
    private LocalDateTime dateTime;

    @ApiModelProperty("是否禁言")
    private Integer banned;

    @ApiModelProperty("群聊头像")
    private String groupHeadImg;

    @ApiModelProperty(value = "未读消息 1有 0无 ",notes = "1时展示小红点")
    private Integer unreadMessage;

    @ApiModelProperty(value = " 未读消息数量")
    private Integer unreadMessageQuantity;

    @ApiModelProperty(value = "最后一条消息")
    private String message;

    @ApiModelProperty(value = "消息类型")
    private Integer  messageTypeId;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
