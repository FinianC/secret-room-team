package com.secret.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("群聊")
public class ChatListVo {

    @ApiModelProperty("群聊名称")
    private String chatName;

    @ApiModelProperty("日期")
    private String dateTime;

    @ApiModelProperty("群聊头像")
    private String groupHeadImg;

    @ApiModelProperty(value = "未读消息 1有 0无 ",notes = "1时展示小红点")
    private Integer unreadMessage;

    @ApiModelProperty(value = " 未读消息数量")
    private Integer unreadMessageQuantity;

    @ApiModelProperty(value = "最后一条消息")
    private String lastMessage;
}
