package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("去支付参数")
@Data
public class ToPayParam {

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    @NotNull
    private Integer orderId;

}
