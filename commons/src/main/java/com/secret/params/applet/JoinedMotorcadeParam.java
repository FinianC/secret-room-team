package com.secret.params.applet;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("加入车队参数")
public class JoinedMotorcadeParam {

    @ApiModelProperty(value = "车队id")
    private Integer motorcadeId;

}
