package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("购买密室票参数")
@Data
public class purchaseTicketParam {

    @ApiModelProperty("票id")
    @NotNull
    private  Integer ticketId;

}
