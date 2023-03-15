package com.secret.params.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("拼车完成参数")
@Data
public class MotorcadeCompleteParam {


    @ApiModelProperty(value = "id")
    @NotNull
    private Integer id;

}
