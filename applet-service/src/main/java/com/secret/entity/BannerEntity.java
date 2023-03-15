package com.secret.entity;

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
 * 小程序横幅
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_banner")
@ApiModel(value="BannerEntity对象", description="小程序横幅")
public class BannerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "图片")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "状态 1上架 0下架")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "是否是外链接 1是 0不是")
    @TableField("extra_links")
    private Integer extraLinks;

    @ApiModelProperty(value = "值 外链地址 或者车队id")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "排序 值越大优先级越高")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "删除状态 0未删除 1删除")
    @TableField("delete_state")
    @TableLogic
    private Integer deleteState;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private Integer updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
