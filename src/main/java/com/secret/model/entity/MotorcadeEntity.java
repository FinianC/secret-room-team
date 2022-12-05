package com.secret.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
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
 * 车队
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_motorcade")
@ApiModel(value="MotorcadeEntity对象", description="车队")
public class MotorcadeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "恐怖等级")
    @TableField("terror_level")
    private Integer terrorLevel;

    @ApiModelProperty(value = "车队类型 密室or剧本等等..")
    @TableField("type_id")
    private Integer typeId;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "拼场日期")
    @TableField("competition_date")
    private LocalDateTime competitionDate;

    @ApiModelProperty(value = "图片 json数组")
    @TableField("pictures")
    private String pictures;

    @ApiModelProperty(value = "车队状态 1组队中 2组队成功 3组队失败")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "禁言 o未禁言 1禁言")
    @TableField("banned")
    private Integer banned;

    @ApiModelProperty(value = "删除状态 0未删除 1 删除")
    @TableField("delete_state")
    @TableLogic
    private Integer deleteState;

    @ApiModelProperty(value = "最大拼团人数")
    @TableField("maximum_number")
    private Integer maximumNumber;

    @ApiModelProperty(value = "成团数")
    @TableField("clustering_number")
    private Integer clusteringNumber;

    @ApiModelProperty(value = "已有人数")
    @TableField("already_existing")
    private Integer alreadyExisting;

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private Integer createUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("update_user")
    private Integer updateUser;


}
