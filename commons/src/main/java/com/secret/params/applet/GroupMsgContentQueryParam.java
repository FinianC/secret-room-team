package com.secret.params.applet;

import com.secret.params.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: secret-room
 * @description: 消息查询参数
 * @author: 陈迪
 * @create: 2023/01/17 17:11
 */
@Data
@ApiModel("消息查询参数")
public class GroupMsgContentQueryParam extends BasePageParam {

    @ApiModelProperty("群聊id")
    @NotNull
    private Integer groupChatId;
}
