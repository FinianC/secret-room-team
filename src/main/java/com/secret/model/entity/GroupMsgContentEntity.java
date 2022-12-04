package com.secret.model.entity;

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
 * 群聊消息
 * </p>
 *
 * @author chenDi
 * @since 2022-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_group_msg_content")
@ApiModel(value="GroupMsgContentEntity对象", description="群聊消息")
public class GroupMsgContentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "车队id")
    @TableField("motorcade_id")
    private String motorcadeId;

    @ApiModelProperty(value = "发送者id")
    @TableField("from_id")
    private Integer fromId;

    @ApiModelProperty(value = "发送人昵称")
    @TableField("from_name")
    private String fromName;

    @ApiModelProperty(value = "发送人头像")
    @TableField("from_profile")
    private String fromProfile;

    @ApiModelProperty(value = "更新人")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "消息类型id")
    @TableField("message_type_id")
    private Integer messageTypeId;

    @ApiModelProperty(value = "删除状态")
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
