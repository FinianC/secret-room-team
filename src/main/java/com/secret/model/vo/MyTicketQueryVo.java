package com.secret.model.vo;

import com.secret.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询我的密室票vo")
@Data
public class MyTicketQueryVo extends BasePageParam {

    @ApiModelProperty(value = "状态  0支付关闭  1待支付 2已退款 3待消费 4已消费")
    private Integer status;

    @ApiModelProperty("搜索框")
    private String search;

}
