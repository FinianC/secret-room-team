package com.secret.vo.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@ApiModel("群聊消息")
public class GroupMessageVo<T> {

    @ApiModelProperty("消息类型 OLD旧消息 NEW 新消息")
    private String type;

    @ApiModelProperty("消息")
    private T groupMsgContentVo;

}
