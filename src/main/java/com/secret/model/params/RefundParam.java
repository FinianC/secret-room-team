package com.secret.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("退款参数")
@Data
public class RefundParam {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("订单号")
    private String orderNum;

    @ApiModelProperty("退款说明")
    private String refundDesc;

}
