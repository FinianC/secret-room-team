package com.secret.model.params;

import com.secret.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("车队查询参数")
public class MotorcadeQueryParam extends BasePageParam {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("车队主题")
    private Integer themeId;
    
}
