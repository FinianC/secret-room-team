package com.secret.params.applet;

import com.secret.params.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询票参数")
@Data
public class TicketQueryParam extends BasePageParam {

    @ApiModelProperty("主题id")
    private Integer themeId;

    @ApiModelProperty("名称")
    private String name;


}
