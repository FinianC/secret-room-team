package com.secret.model.params;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("车队修改参数")
@Data
public class MotorcadeModifyParam {


    @ApiModelProperty(value = "id")
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "恐怖等级")
    private Integer terrorLevel;

    @ApiModelProperty(value = "车队类型 密室or剧本等等..")
    private Integer typeId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "拼场日期")
    private LocalDateTime competitionDate;

    @ApiModelProperty(value = "图片 json数组")
    private String pictures;

    @ApiModelProperty(value = "最大拼团人数")
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    private Integer clusteringNumber;

    @ApiModelProperty(value = "已有人数")
    private Integer alreadyExisting;


}
