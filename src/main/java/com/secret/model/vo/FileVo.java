package com.secret.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文件vo")
public class FileVo {

    @ApiModelProperty(value = "文件名称")
    private Integer id;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String url;

    @ApiModelProperty(value = "文件类型 1图片 2其他")
    private Integer type;

}