package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("购买密室票参数")
@Data
public class purchaseTicketParam {

    @ApiModelProperty(value = "票id" ,required = true)
    @NotNull
    private  Integer ticketId;
    
    @ApiModelProperty(value = "电话号码", required = true)
    @NotBlank
    private  String phone;

    @ApiModelProperty(value ="数量", required = true)
    @NotNull
    private  Integer quantity;

}
