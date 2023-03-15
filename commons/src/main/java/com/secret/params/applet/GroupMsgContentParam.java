package com.secret.params.applet;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("发送消息")
@Data
public class GroupMsgContentParam {

    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型编号
     */
    private Integer messageTypeId;

}
