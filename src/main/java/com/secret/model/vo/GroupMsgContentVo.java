package com.secret.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("消息内容")
public class GroupMsgContentVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "车队id")
    private Integer motorcadeId;

    @ApiModelProperty(value = "发送者id")
    private Integer fromId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "发送人昵称")
    private String fromName;

    @ApiModelProperty(value = "发送人头像")
    private String fromProfile;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "消息类型id")
    private Integer messageTypeId;

    @ApiModelProperty(value = "显示时间")
    private LocalDateTime displayTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
