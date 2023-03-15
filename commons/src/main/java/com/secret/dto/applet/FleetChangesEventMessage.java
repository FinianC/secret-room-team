package com.secret.dto.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("车队改变消息")
@AllArgsConstructor
@NoArgsConstructor
public class FleetChangesEventMessage {

    @ApiModelProperty("事件类型 离开or加入...")
    private Integer eventType;

    @ApiModelProperty("车队id")
    private Integer fleetId;

    @ApiModelProperty("用户id")
    private Integer userId;



}
