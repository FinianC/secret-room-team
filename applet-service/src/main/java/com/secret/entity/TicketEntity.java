package com.secret.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ApiModelProperty(value = "购买须知")
    @TableField("purchase_instructions")
    private String purchaseInstructions;

    @ApiModelProperty(value = "商品介绍")
    @TableField("introduce")
    private String introduce;

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
