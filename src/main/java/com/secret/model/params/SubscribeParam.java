package com.secret.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("订阅消息参数")
@Data
public class SubscribeParam {

    @ApiModelProperty(value = "车队id")
    private Integer motorcadeId;


    @ApiModelProperty(value = "form_id")
    private String formId;

}
