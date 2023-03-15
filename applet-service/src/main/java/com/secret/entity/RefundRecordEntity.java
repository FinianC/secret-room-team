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
 * @since 2023-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_refund_record")
@ApiModel(value="RefundRecordEntity对象", description="")
public class RefundRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty(value = "支付id")
    @TableField("pay_id")
    private Integer payId;

    @ApiModelProperty(value = "退单号")
    @TableField("out_refund_no")
    private String outRefundNo;

    @ApiModelProperty(value = "总金额")
    @TableField("total_fee")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "退款金额")
    @TableField("refund_fee")
    private BigDecimal refundFee;

    @ApiModelProperty(value = "退款说明")
    @TableField("refund_desc")
    private String refundDesc;

    @ApiModelProperty(value = "退款状态 0失败 1成功 2退款中")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "删除标记")
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
    private LocalDateTime updateUser;


}
