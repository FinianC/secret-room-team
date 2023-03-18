package com.secret.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenDi
 * @since 2023-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_ticket")
@ApiModel(value="TicketEntity对象", description="")
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "主题")
    @TableField("theme_id")
    private Integer themeId;

    @ApiModelProperty(value = "恐怖等级")
    @TableField("terror_id")
    private Integer terrorId;

    @ApiModelProperty(value = "现价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "原价")
    @TableField("original_price")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "封面图")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "门店id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "库存")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty(value = "已售")
    @TableField("sold")
    private Integer sold;

    @ApiModelProperty(value = "总数")
    @TableField("total")
    private Integer total;

    @ApiModelProperty(value = "删除标记 1删除 0未删除")
    @TableField("delete_state")
    @TableLogic
    private Integer deleteState;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("update_user")
    private String updateUser;


}
