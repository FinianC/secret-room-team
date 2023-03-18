package com.secret.vo.applet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel("票vo")
@Data
public class TicketVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "主题")
    private Integer themeId;

    @ApiModelProperty(value = "主题名称")
    private String themeName;

    @ApiModelProperty(value = "恐怖等级id")
    private Integer terrorId;

    @ApiModelProperty(value = "恐怖等级")
    private String terrorName;

    @ApiModelProperty(value = "现价")
    private BigDecimal price;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "封面图")
    private String picture;

    @ApiModelProperty(value = "门店id")
    private Integer storeId;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "已售")
    private Integer sold;

    @ApiModelProperty(value = "购买须知")
    private String purchaseInstructions;

    @ApiModelProperty(value = "商品介绍")
    private String introduce;

    @ApiModelProperty(value = "最大拼团人数")
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    private Integer clusteringNumber;

    @ApiModelProperty(value = "时间 单位：分钟")
    private Integer time;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "密室店铺")
    private StoreVo storeVo;

}
