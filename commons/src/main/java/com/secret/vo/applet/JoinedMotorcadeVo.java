package com.secret.vo.applet;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("已加入车队用户vo")
@Data
public class JoinedMotorcadeVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "车队id")
    private Integer motorcadeId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String headerImg;

}
