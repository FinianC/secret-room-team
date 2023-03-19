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
 * @since 2023-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_my_ticket")
@ApiModel(value="MyTicketEntity对象", description="")
public class MyTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "票id")
    @TableField("ticket_id")
    private Integer ticketId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "付款金额")
    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "订单号")
    @TableField("order_num")
    private String orderNum;

    @ApiModelProperty(value = "状态  0支付关闭  1待支付 2已退款 3待消费 4已消费")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "删除状态")
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