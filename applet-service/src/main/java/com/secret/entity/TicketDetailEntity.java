package com.secret.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_ticket_detail")
@ApiModel(value="TicketDetailEntity对象", description="")
public class TicketDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "密室id")
    @TableField("ticket_id")
    private Integer ticketId;

    @ApiModelProperty(value = "最大拼团人数")
    @TableField("maximum_number")
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    @TableField("clustering_number")
    private Integer clusteringNumber;

    @ApiModelProperty(value = "时间 单位 分钟")
    @TableField("time")
    private Integer time;

    @ApiModelProperty(value = "购买须知")
    @TableField("purchase_instructions")
    private String purchaseInstructions;

    @ApiModelProperty(value = "商品介绍")
    @TableField("introduce")
    private String introduce;


}
