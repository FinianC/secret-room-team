package com.secret.vo.applet;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("我的密室票/我的订单")
public class MyTicketVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "状态  0支付关闭  1待支付 2已退款 3待消费 4已消费")
    private Integer status;


    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("密室票信息")
    private TicketVo ticketVo;
}
