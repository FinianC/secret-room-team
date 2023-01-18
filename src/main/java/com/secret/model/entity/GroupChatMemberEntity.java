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
 * 
 * </p>
 *
 * @author chenDi
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_group_chat_member")
@ApiModel(value="GroupChatMemberEntity对象", description="")
public class GroupChatMemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "成员id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "群主 1")
    @TableField(" is_group_leader")
    private Integer  isGroupLeader;

    @ApiModelProperty(value = "群id")
    @TableField("group_id")
    private Integer groupId;

    @ApiModelProperty(value = "第一次进入群聊消息")
    @TableField("first_message_id")
    private Integer firstMessageId;

    @ApiModelProperty(value = "上一次收到消息的id")
    @TableField("last_message_id")
    private Integer lastMessageId;

    @ApiModelProperty(value = "删除 1")
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
