package com.secret.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("车队vo")
@Data
public class MotorcadeVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "主题")
    private String theme;

    @ApiModelProperty(value = "恐怖等级")
    private Integer terrorLevel;

    @ApiModelProperty(value = "恐怖等级")
    private Integer terrorLevelName;

    @ApiModelProperty(value = "车队类型 密室or剧本等等..")
    private Integer typeId;

    @ApiModelProperty(value = "车队名称")
    private Integer typeName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "拼场日期")
    private LocalDateTime competitionDate;

    @ApiModelProperty(value = "图片 json数组")
    private String pictures;

    @ApiModelProperty(value = "车队状态 1组队中 2组队成功 3组队失败")
    private Integer status;

    @ApiModelProperty(value = "最大拼团人数")
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    private Integer clusteringNumber;

    @ApiModelProperty(value = "已有人数")
    private Integer alreadyExisting;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "创建人名称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String headerImg;

    @ApiModelProperty(value = "是否加入 1加入 0未加入")
    private Integer joined;

    @ApiModelProperty("车队内成员")
    private List<JoinedMotorcadeVo> joinedMotorcadeVos;
}
