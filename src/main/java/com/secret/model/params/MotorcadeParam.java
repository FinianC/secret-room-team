package com.secret.model.params;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel("车队创建参数")
@Data
public class MotorcadeParam {

    @ApiModelProperty(value = "标题")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "恐怖等级")
    @NotBlank
    private Integer terrorLevel;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "主题")
    private String theme;

    @ApiModelProperty(value = "车队类型 密室or剧本等等..")
    @NotBlank
    private Integer typeId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "拼场日期")
    @NotBlank
    private String competitionDate;

    @ApiModelProperty(value = "图片 json数组")
    private String pictures;


    @ApiModelProperty(value = "最大拼团人数")
    @NotBlank
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    @NotBlank
    private Integer clusteringNumber;

    @ApiModelProperty(value = "已有人数")
    @NotBlank
    private Integer alreadyExisting;


}
